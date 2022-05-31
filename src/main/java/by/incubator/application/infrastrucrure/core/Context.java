package by.incubator.application.infrastrucrure.core;

import by.incubator.application.infrastrucrure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
