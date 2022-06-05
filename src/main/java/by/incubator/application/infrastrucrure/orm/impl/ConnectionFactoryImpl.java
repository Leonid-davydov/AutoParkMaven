package by.incubator.application.infrastrucrure.orm.impl;

import by.incubator.application.infrastrucrure.core.annotations.InitMethod;
import by.incubator.application.infrastrucrure.core.annotations.Property;
import by.incubator.application.infrastrucrure.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {}

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        connection =  DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
