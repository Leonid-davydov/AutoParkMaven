package infrastrucrure.core;

import infrastrucrure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
