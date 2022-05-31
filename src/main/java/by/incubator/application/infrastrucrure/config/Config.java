package by.incubator.application.infrastrucrure.config;

import by.incubator.application.infrastrucrure.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
