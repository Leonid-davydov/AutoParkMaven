package by.incubator.application.service;

import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;
import by.incubator.application.infrastrucrure.orm.EntityManager;

import java.util.List;

public class VehicleService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).get();
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicle) {
        return entityManager.save(vehicle);
    }
}
