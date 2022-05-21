package application.by.incubator.Service;

import application.by.incubator.Vehicle.Vehicle;
import application.by.incubator.infrastrucrure.core.annotations.Autowired;

import java.util.List;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public Workroom() {}

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicles(List<Vehicle> vehicles) {
        System.out.println("Broken cars:");
        for (Vehicle vehicle : vehicles) {
            if (mechanic.isBroken(vehicle))
                System.out.println(vehicle);
        }

        System.out.println("Not broken cars:");
        for (Vehicle vehicle : vehicles) {
            if (!mechanic.isBroken(vehicle))
                System.out.println(vehicle);
        }
    }
}
