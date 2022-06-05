package by.incubator.application.MechanicService;

import by.incubator.application.Vehicle.Vehicle;
import by.incubator.application.entity.Vehicles;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer {
    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        return new HashMap<>();
    }

    @Override
    public void repair(Vehicles vehicle) {}

    @Override
    public boolean isBroken(Vehicles vehicle) {
        return false;
    }
}
