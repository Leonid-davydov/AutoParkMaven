package by.incubator.application.checker;

import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.orm.EntityManager;
import by.incubator.application.infrastrucrure.threads.annotations.Schedule;
import by.incubator.application.mechanicService.Workroom;

import java.util.List;

public class VehiclesChecker {
    public VehiclesChecker() {
    }

    @Schedule(delta = 10000, timeout = 10000)
    public void vehiclesFromDbToWorkroom(Context context) {
        EntityManager manager = context.getObject(EntityManager.class);
        List<Vehicles> vehicles = manager.getAll(Vehicles.class);
        context.getObject(Workroom.class).checkAllVehicles(vehicles);
    }
}
