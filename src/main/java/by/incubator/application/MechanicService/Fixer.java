package by.incubator.application.MechanicService;

import by.incubator.application.Vehicle.Vehicle;

import java.util.Map;

public interface Fixer {
    Map<String, Integer> detectBreaking(Vehicle vehicle);

    void repair(Vehicle vehicle);

    default boolean detectAndRepair(Vehicle vehicle) {
        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
            repair(vehicle);
            return true;
        }
        return false;
    }

    boolean isBroken(Vehicle vehicle);
}
