package application.by.incubator.infrastrucrure.core.impl;

import application.by.incubator.infrastrucrure.configurators.ObjectConfigurator;
import application.by.incubator.infrastrucrure.configurators.impl.AutowiredObjectConfigurator;
import application.by.incubator.infrastrucrure.configurators.impl.PropertyObjectConfigurator;
import application.by.incubator.infrastrucrure.core.Context;
import application.by.incubator.infrastrucrure.core.ObjectFactory;
import application.by.incubator.infrastrucrure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> objConfigs = context
                .getConfig()
                .getScanner()
                .getSubTypesOf(ObjectConfigurator.class);
        objConfigs.add(AutowiredObjectConfigurator.class);
        objConfigs.add(PropertyObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> objConfig : objConfigs) {
            Constructor<? extends ObjectConfigurator> constructor = objConfig.getConstructor();
            objectConfigurators.add(constructor.newInstance());
        }
    }

    private <T> T create(Class<T> implementation) throws Exception {
        Constructor<T> constructor = implementation.getConstructor();
        return constructor.newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator objectConfigurator : objectConfigurators) {
            objectConfigurator.configure(object, context);
        }
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        Method[] methods = implementation.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T newObject = create(implementation);
        configure(newObject);
        initialize(implementation, newObject);
        return newObject;
    }
}
