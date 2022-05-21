package application.by.incubator.infrastrucrure.configurators;

import application.by.incubator.infrastrucrure.core.Context;

public interface ObjectConfigurator {
    void configure(Object object, Context context);
}
