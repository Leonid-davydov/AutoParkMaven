package by.incubator.application.infrastrucrure.core.impl;

import by.incubator.application.infrastrucrure.configurators.ObjectConfigurator;
import by.incubator.application.infrastrucrure.configurators.ProxyConfigurator;
import by.incubator.application.infrastrucrure.configurators.impl.AutowiredObjectConfigurator;
import by.incubator.application.infrastrucrure.configurators.impl.PropertyObjectConfigurator;
import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.core.ObjectFactory;
import by.incubator.application.infrastrucrure.core.Scanner;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        Set<Class<? extends ObjectConfigurator>> objConfigs = scanner.getSubTypesOf(ObjectConfigurator.class);
        Set<Class<? extends ProxyConfigurator>> proxyConfigs = scanner.getSubTypesOf(ProxyConfigurator.class);

        objConfigs.add(AutowiredObjectConfigurator.class);
        objConfigs.add(PropertyObjectConfigurator.class);

        for (Class<? extends ObjectConfigurator> objConfig : objConfigs) {
            Constructor<? extends ObjectConfigurator> constructor = objConfig.getConstructor();
            objectConfigurators.add(constructor.newInstance());
        }

        for (Class<? extends ProxyConfigurator> proxyConfig : proxyConfigs) {
            Constructor<? extends ProxyConfigurator> constructor = proxyConfig.getConstructor();
            proxyConfigurators.add(constructor.newInstance());
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T newObject = create(implementation);
        configure(newObject);
        initialize(implementation, newObject);
        newObject = makeProxy(implementation, newObject);

        return newObject;
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = proxyConfigurator.makeProxy(object, implClass, context);
        }

        return object;
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
}
