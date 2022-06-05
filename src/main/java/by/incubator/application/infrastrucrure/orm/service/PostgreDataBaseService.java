package by.incubator.application.infrastrucrure.orm.service;

import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;
import by.incubator.application.infrastrucrure.orm.ConnectionFactory;
import by.incubator.application.infrastrucrure.orm.annotations.Column;
import by.incubator.application.infrastrucrure.orm.annotations.ID;
import by.incubator.application.infrastrucrure.orm.annotations.Table;
import by.incubator.application.infrastrucrure.orm.enums.SqlFieldType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class PostgreDataBaseService {
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "   SELECT FROM information_schema.sequences \n" +
                    "   WHERE  sequence_schema = 'public'\n" +
                    "   AND    sequence_name   = '%s'\n" +
                    ");";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %S\n" +
                    "    INCREMENT 1\n" +
                    "    START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "   SELECT FROM information_schema.tables \n" +
                    "   WHERE  table_schema = 'public'\n" +
                    "   AND    table_name   = '%s'\n" +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE %s (\n" +
                    "    %s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "    %S\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "    VALUES (%s)\n" +
                    "    RETURNING %s ;";
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Context context;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    private Map<String, String> insertByClassPattern;

    public PostgreDataBaseService() {
    }

    @InitMethod
    public void init() {
        classToSql = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(), SqlFieldType::getSqlType));

        insertPatternByClass = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(), SqlFieldType::getInsertPattern));

        if (!isIdSeq()) {
            createIdSeq();
        }

        Set<Class<?>> entities = context.getConfig()
                .getScanner()
                .getReflections()
                .getTypesAnnotatedWith(Table.class);

        validateEntities(entities);
        checkTables(entities);

        insertByClassPattern = new HashMap<>();
        entities.stream().forEach(entity -> insertByClassPattern.put(entity.getName(), getInsertQuery(entity)));
    }

    public Long save(Object obj) {
        Long id;
        Field idField = getIdField(obj.getClass().getDeclaredFields());
        String idFieldName = obj.getClass().getDeclaredAnnotation(ID.class).name();
        Object[] values = getValues(obj);
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            resultSet.next();
            id = resultSet.getLong(idFieldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            idField.setAccessible(true);
            idField.set(obj, id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return id;
    }

    @SneakyThrows
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        checkTableAnnotation(clazz);

        String sql = "SELECT * FROM " + clazz.getDeclaredAnnotation(Table.class).name() +
                " WHERE " + clazz.getDeclaredAnnotation(ID.class).name() + " = " + id;

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return Optional.of(makeObject(resultSet, clazz));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @SneakyThrows
    public <T> List<T> getAll(Class<T> clazz) {
        List<T> list = new ArrayList<>();

        checkTableAnnotation(clazz);

        String sql = "SELECT * FROM " + clazz.getDeclaredAnnotation(Table.class).name();

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while(resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
        }

        return list;
    }

    private boolean isIdSeq() {
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private void createIdSeq() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void validateEntities(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            Field[] fields = entity.getDeclaredFields();

            if (!validateID(fields)) {
                throw new IllegalStateException("Class " + entity.getName() + " doesn't contain Long 'ID' field");
            }

            if (!validateColumnFields(fields)) {
                throw new IllegalStateException("Class " + entity.getName() + " contains fields 'Column'" +
                        " with identical names or primitive classes");
            }
        }
    }

    private boolean validateID(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class) && field.getType().equals(Long.class)) {
                return true;
            }
        }

        return false;
    }

    private boolean validateColumnFields(Field[] fields) {
        Set<String> names = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                if (!names.add(field.getAnnotation(Column.class).name()) || field.getType().isPrimitive()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void checkTables(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            if (!isTable(entity.getDeclaredAnnotation(Table.class).name())) {
                createTable(entity);
            }
        }
    }

    private boolean isTable(String name) {
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, name);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private void createTable(Class<?> entity) {
        Field[] declaredFields = entity.getDeclaredFields();

        String tableName = entity.getDeclaredAnnotation(Table.class).name();
        String idField = entity.getDeclaredAnnotation(ID.class).name();
        String fields = buildFields(declaredFields);
        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String buildFields(Field[] fields) {
        StringBuilder fieldsBuilder = new StringBuilder();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                fieldsBuilder.append(buildField(field.getDeclaredAnnotation(Column.class), field.getType().getName()));
            }
        }

        return fieldsBuilder.toString();
    }

    private String buildField(Column column, String className) {
        return ", " +
                column.name() +
                " " + classToSql.get(className) +
                (column.nullable() ? "" : " NOT NULL") +
                (column.unique() ? " UNIQUE" : "");
    }

    private String getInsertQuery(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        String tableName = clazz.getDeclaredAnnotation(Table.class).name();
        String idFieldName = clazz.getDeclaredAnnotation(ID.class).name();
        String insertFields = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getDeclaredAnnotation(Column.class).name())
                .reduce((name1, name2) -> name1 + ", " + name2).get();

        String values = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> insertPatternByClass.get(field.getType().getName()))
                .reduce((name1, name2) -> name1 + ", " + name2).get();

        return String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
    }

    private Object[] getValues(Object obj) {
        Object[] values = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);

                    try {
                        return field.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return new Object();
                })
                .toArray();

        return values;
    }

    private Field getIdField(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                return field;
            }
        }

        throw new IllegalStateException("Table hasn't 'ID' field");
    }

    private void checkTableAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " hasn't 'Table' annotation");
        }
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        Method method;
        T obj = clazz.getConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                String idName = field.getAnnotation(ID.class).name();
                method = getMethodForType(field.getType());
                setField(field, obj, method.invoke(resultSet, idName));
            }

            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                method = getMethodForType(field.getType());
                setField(field, obj, method.invoke(resultSet, columnName));
            }
        }

        return obj;
    }

    @SneakyThrows
    private Method getMethodForType(Class<?> type) {
        if (type.equals(Integer.class)) {
            return ResultSet.class.getMethod("getInt", String.class);
        } else {
            return ResultSet.class.getMethod("get" + type.getSimpleName(), String.class);
        }
    }

    @SneakyThrows
    private <T> void setField(Field field, T obj, Object value) {
        field.setAccessible(true);
        field.set(obj, value);
    }
}
