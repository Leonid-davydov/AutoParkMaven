package by.incubator.application.infrastrucrure.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}
