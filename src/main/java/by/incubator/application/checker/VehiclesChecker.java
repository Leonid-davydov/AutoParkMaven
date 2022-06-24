package by.incubator.application.checker;

import by.incubator.application.dto.OrdersDto;
import by.incubator.application.dto.VehicleTypeService;
import by.incubator.application.entity.Orders;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.Context;
import by.incubator.application.infrastrucrure.orm.EntityManager;
import by.incubator.application.infrastrucrure.threads.annotations.Schedule;
import by.incubator.application.mechanicService.Workroom;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class VehiclesChecker {
    private Date dateOfLastChek;
    private List<OrdersDto> beforeChek;
    private List<OrdersDto> afterChek;
    private VehicleTypeService vehicleTypeService = new VehicleTypeService();

    public VehiclesChecker() {
    }

    @Schedule(delta = 10000, timeout = 10000)
    public void vehiclesFromDbToWorkroom(Context context) {
        EntityManager manager = context.getObject(EntityManager.class);
        List<Vehicles> vehicles = manager.getAll(Vehicles.class);
        context.getObject(Workroom.class).checkAllVehicles(vehicles);
    }

    @Schedule(delta = 10000, timeout = 10000)
    public void runServerChecker(Context context) {
        EntityManager manager = context.getObject(EntityManager.class);
        List<Vehicles> vehicles = manager.getAll(Vehicles.class);

        beforeChek = vehicleTypeService.getOrders();
        context.getObject(Workroom.class).repairAllVehicles();
        dateOfLastChek = new Date();
        afterChek = vehicleTypeService.getOrders();
    }

    public long getFrequency() {
        return 10000;
    }
}
