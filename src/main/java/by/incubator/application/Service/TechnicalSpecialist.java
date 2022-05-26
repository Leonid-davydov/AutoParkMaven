package by.incubator.application.Service;

import by.incubator.application.Engins.DieselEngine;
import by.incubator.application.Engins.ElectricalEngine;
import by.incubator.application.Engins.GasolineEngine;
import by.incubator.application.Vehicle.*;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;

public class TechnicalSpecialist {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public TechnicalSpecialist() {}

    @InitMethod
    public void init() {}

    static public boolean validateManufactureYear(int year) {
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR && year <= 9999;
    }

    static public boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    static public boolean validateWeight(int weight) {
        return weight >= 0;
    }

    static public boolean validateColor(Color color) {
        return color != null;
    }

    static public boolean validateVehicleType(VehicleType type) {
        return type != null && type.getName() != null && type.getCoefficient() >= 0;
    }

    static public boolean validateModelName(String name) {
        return name != null && !name.equals("");
    }

    static public boolean validateRegistrationNumber(String number) {
        // ???
        if (number == null)
            return true;
        for (int i = 0; i < 4; i++){
            if (number.charAt(i) < 48 || number.charAt(i) > 57) {
                return false;
            }
        }
        if (number.charAt(4) != ' ') {
            return false;
        }
        for (int i = 5; i < 7; i++){
            if (number.charAt(i) < 65 || number.charAt(i) > 90) {
                return false;
            }
        }
        if (number.charAt(7) != '-') {
            return false;
        }
        return number.charAt(8) >= 48 && number.charAt(8) <= 57;
    }

    static public boolean validateDieselEngine(DieselEngine engine) {
        if (engine.getEngineCapacity() <= 0)
            return false;
        if (engine.getFuelTankCapacity() <= 0)
            return false;
        return engine.getFuelConsumptionPer100() > 0;
    }

    static public boolean validateGasolineEngine(GasolineEngine engine) {
        if (engine.getEngineCapacity() <= 0)
            return false;
        if (engine.getFuelTankCapacity() <= 0)
            return false;
        return engine.getFuelConsumptionPer100() > 0;
    }

    static public boolean validateElectricalEngine(ElectricalEngine engine) {
        if (engine.getBatterySize() <= 0)
            return false;
        return engine.getElectricityConsumption() > 0;
    }
}
