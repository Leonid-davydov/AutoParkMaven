package infrastrucrure.core.impl;

import infrastrucrure.configurators.ObjectConfigurator;
import infrastrucrure.core.Context;
import infrastrucrure.core.ObjectFactory;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Set classes = context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class);
        // доделать

    }

    @Override
    public <T> T createObject(Class<T> implementation) {
        return null;
    }
}
