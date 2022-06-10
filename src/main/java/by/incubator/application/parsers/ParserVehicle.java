package by.incubator.application.parsers;

import by.incubator.application.Rent.Rent;
import by.incubator.application.Vehicle.Vehicle;
import by.incubator.application.Vehicle.VehicleType;
import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;

import java.util.List;

public interface ParserVehicle {
    List<Types> loadTypes(String inFile);

    List<Rents> loadRents(List<Vehicles> vehicles, String inFile);

    List<Vehicles> loadVehicles(List<Types> vehicleTypes, String inFile);
}
