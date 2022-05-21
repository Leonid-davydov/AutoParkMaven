package application.by.incubator.infrastrucrure.config;

import application.by.incubator.infrastrucrure.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
