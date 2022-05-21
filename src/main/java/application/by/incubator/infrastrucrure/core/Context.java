package application.by.incubator.infrastrucrure.core;

import application.by.incubator.infrastrucrure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
