package by.incubator.application.engins;

public class DieselEngine extends CombustionEngine {
    public DieselEngine(double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super("Diesel", 1.2, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }
}
