package by.incubator.application;

import by.incubator.application.engins.DieselEngine;
import by.incubator.application.engins.ElectricalEngine;
import by.incubator.application.engins.GasolineEngine;
import by.incubator.application.engins.Startable;
import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.service.TypesService;
import by.incubator.application.vehicle.Color;
import by.incubator.application.vehicle.Vehicle;
import by.incubator.application.vehicle.VehicleType;

public class Transformer {
    @Autowired
    TypesService typesService;



    public Vehicle transformVehicle(Vehicles v) {
        int id = Math.toIntExact(v.getId());
        VehicleType vehicleType = transformType(typesService.get(v.getId()));
        String modelName = v.getModel();
        String registrationNumber = v.getRegistrationNumber();
        int weight = v.getWeight();
        int manufactureYear = v.getManufactureYear();
        int mileage = v.getMileage();
        Color color = null;
        Startable engine = null;

        for (Color c : Color.values()) {
            if (v.getColor().equals(c.toString())) {
                color = c;
                break;
            }
        }

        if (v.getEngineType().equals("Electrical")) {
            engine = new ElectricalEngine(0, 0);
        } else if (v.getEngineType().equals("Diesel")) {
            engine = new DieselEngine(0, 0, 0);
        } else if (v.getEngineType().equals("Gasoline")) {
            engine = new GasolineEngine(0, 0, 0);
        }

        return new Vehicle(id, vehicleType, modelName, registrationNumber, weight,
                manufactureYear, mileage, color, engine);
    }

    public VehicleType transformType(Types t) {
        return new VehicleType(Math.toIntExact(t.getId()), t.getName(), t.getCoefTaxes());
    }

}
