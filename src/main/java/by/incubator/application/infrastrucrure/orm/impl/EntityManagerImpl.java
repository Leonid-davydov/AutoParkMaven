package by.incubator.application.infrastrucrure.orm.impl;

import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.orm.ConnectionFactory;
import by.incubator.application.infrastrucrure.orm.EntityManager;
import by.incubator.application.infrastrucrure.orm.service.PostgreDataBaseService;

import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBaseService dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }

    @Override
    public void delete(Object object) {
        dataBaseService.delete(object);
    }
}
