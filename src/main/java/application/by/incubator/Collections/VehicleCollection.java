package application.by.incubator.Collections;

import application.by.incubator.Vehicle.ParserVehicleFromFile;
import application.by.incubator.Vehicle.Vehicle;
import application.by.incubator.Vehicle.VehicleType;
import application.by.incubator.infrastrucrure.core.annotations.Autowired;
import application.by.incubator.infrastrucrure.core.annotations.InitMethod;

import java.util.ArrayList;
import java.util.List;

public class VehicleCollection {
    private List<VehicleType> vehicleTypes = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    @Autowired
    private ParserVehicleFromFile parser;

    public VehicleCollection() {}


    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public ParserVehicleFromFile getParser() {
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
