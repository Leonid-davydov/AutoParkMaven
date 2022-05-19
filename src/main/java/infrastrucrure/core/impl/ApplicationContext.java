package infrastrucrure.core.impl;

import infrastrucrure.config.Config;

import infrastrucrure.core.Cache;
import infrastrucrure.core.Context;
import infrastrucrure.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context{
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementtion) {
        this.config = new by.incubator.autopark.infrastrucrure.config.impl.JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementtion);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        T newObject;

        if (cache.contains(type))
            return cache.get(type);

        if (type.isInterface()) {
            Class<?> clazz = config.getImplementation(type);
            newObject = (T) factory.createObject(clazz);
        } else {
            newObject = (T) factory.createObject(type);
        }

        return newObject;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
