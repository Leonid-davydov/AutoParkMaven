package by.incubator.application.infrastrucrure.core.impl;

import by.incubator.application.infrastrucrure.config.Config;
import by.incubator.application.infrastrucrure.config.impl.JavaConfig;
import by.incubator.application.infrastrucrure.core.Cache;
import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context{
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (cache.get(type) != null) return cache.get(type);
        if (type.getConstructors().length != 0) {
            cache.put(type, factory.createObject(type));
            return cache.get(type);
        }
        Class<T> clazz = (Class<T>) config.getImplementation(type);
        cache.put(clazz, factory.createObject(clazz));
        return (T) cache.get(clazz);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
