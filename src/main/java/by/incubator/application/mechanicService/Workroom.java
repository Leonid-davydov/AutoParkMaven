package by.incubator.application.mechanicService;

import by.incubator.application.entity.Orders;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.service.OrdersService;

import java.util.List;

public class Workroom {
    @Autowired
    private Fixer mechanic;
    @Autowired
    private OrdersService ordersService;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicles(List<Vehicles> vehicles) {
        System.out.println("Broken cars:");
        for (Vehicles vehicle : vehicles) {
            if (mechanic.isBroken(vehicle))
                System.out.println(vehicle);
        }

        System.out.println("Not broken cars:");
        for (Vehicles vehicle : vehicles) {
            if (!mechanic.isBroken(vehicle))
                System.out.println(vehicle);
        }
    }

    public void repairAllVehicles() {
        for (Orders orders : ordersService.getAll()) {
            ordersService.delete(orders);
        }
    }
}
