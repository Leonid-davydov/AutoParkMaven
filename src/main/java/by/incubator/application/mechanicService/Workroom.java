package by.incubator.application.mechanicService;

import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;

import java.util.List;

public class Workroom {
    @Autowired
    private Fixer mechanic;

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
}
