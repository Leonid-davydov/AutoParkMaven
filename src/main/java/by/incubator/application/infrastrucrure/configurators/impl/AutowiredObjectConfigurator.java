package by.incubator.application.infrastrucrure.configurators.impl;

import by.incubator.application.infrastrucrure.configurators.ObjectConfigurator;
import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getDeclaredAnnotation(Autowired.class);
            if (autowired != null){
                field.setAccessible(true);
                field.set(object, context.getObject(field.getType()));
            }
        }
    }
}
