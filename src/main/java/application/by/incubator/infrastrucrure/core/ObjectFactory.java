package application.by.incubator.infrastrucrure.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}
