package by.incubator.application.infrastrucrure.configurators;

import by.incubator.application.infrastrucrure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
