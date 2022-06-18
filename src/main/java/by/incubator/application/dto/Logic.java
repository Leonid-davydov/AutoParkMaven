package by.incubator.application.dto;

import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.service.RentsService;

public class Logic {
    @Autowired
    RentsService rentsService;

    public double getTax(Vehicles vehicles, Types types) {
        return ((vehicles.getWeight() * 0.0013d) + (types.getCoefTaxes() * getEngineTaxCoefficient(vehicles) * 30) + 5);
    }

    public double getEngineTaxCoefficient(Vehicles vehicles) {
        String engineType = vehicles.getEngineType();
        double engineTaxCoefficient = 0.0;

        switch (engineType) {
            case ("Diesel"):
                engineTaxCoefficient = 1.2;
                break;
            case ("Electrical"):
                engineTaxCoefficient = 0.1;
                break;
            case ("Gasoline"):
                engineTaxCoefficient = 1.1;
                break;
        }

        return engineTaxCoefficient;
    }

    public double getMaxKm(Vehicles vehicles) {
        return vehicles.getVolume() * 100 / vehicles.getConsumption();
    }

    public double getIncome(Vehicles vehicles) {
        long id = vehicles.getTypesId();
        double income = 0.0;

        for (Rents rents : rentsService.getAll()) {
            if (rents.getCarId() == id){
                income += rents.getCost();
            }
        }

        return income;
    }
}
