package application.by.incubator.infrastrucrure.configurators.impl;

import application.by.incubator.infrastrucrure.configurators.ObjectConfigurator;
import application.by.incubator.infrastrucrure.core.Context;
import application.by.incubator.infrastrucrure.core.annotations.Autowired;
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
