package by.incubator.application.parsers;

import by.incubator.application.Vehicle.Vehicle;
import by.incubator.application.entity.Vehicles;

import java.util.Map;

public interface ParserBreakings {
    Map<String, Integer> detectBreaking(Vehicles vehicle);

    void repair(Vehicles vehicle);

    boolean isBroken(Vehicles vehicle);
}
