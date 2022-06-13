package by.incubator.application.mechanicService;

import by.incubator.application.entity.Vehicles;

import java.util.Map;

public interface Fixer {
    Map<String, Integer> detectBreaking(Vehicles vehicle);

    void repair(Vehicles vehicle);

    default boolean detectAndRepair(Vehicles vehicle) {
        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
            repair(vehicle);
            return true;
        }
        return false;
    }

    boolean isBroken(Vehicles vehicle);
}
