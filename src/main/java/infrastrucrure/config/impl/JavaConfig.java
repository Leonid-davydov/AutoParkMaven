package by.incubator.autopark.infrastrucrure.config.impl;

import infrastrucrure.config.Config;
import infrastrucrure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
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
