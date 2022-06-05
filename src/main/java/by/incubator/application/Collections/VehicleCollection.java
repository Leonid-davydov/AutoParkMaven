package by.incubator.application.Collections;

import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.parsers.impl.ParserVehicleFromFile;
import by.incubator.application.Vehicle.Vehicle;
import by.incubator.application.Vehicle.VehicleType;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;

import java.util.ArrayList;
import java.util.List;

public class VehicleCollection {
    private List<Types> vehicleTypes = new ArrayList<>();
    private List<Vehicles> vehicles = new ArrayList<>();
    @Autowired
    private ParserVehicle parser;

    public VehicleCollection() {}

    public List<Types> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<Types> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<Vehicles> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicles> vehicles) {
        this.vehicles = vehicles;
    }

    public ParserVehicle getParser() {
        return parser;
    }

    public void setParser(ParserVehicleFromFile parser) {
        this.parser = parser;
    }

    @InitMethod
    public void init() {
        vehicleTypes = parser.loadTypes("types");
        vehicles = parser.loadVehicles(vehicleTypes,"vehicles");
    }
}
