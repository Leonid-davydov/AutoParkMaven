package application.by.incubator.infrastrucrure.config.impl;

import application.by.incubator.infrastrucrure.config.Config;
import application.by.incubator.infrastrucrure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {

        if (interfaceToImplementation.containsKey(target)) {
            return (Class<? extends T>) interfaceToImplementation.get(target);
        }

        Set set = scanner.getSubTypesOf(target);

        if (set.size() != 1) {
            throw new RuntimeException("target interface has 0 or more then one impl");
        } else {
            return (Class<? extends T>) set.stream().findFirst().get();
        }
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
