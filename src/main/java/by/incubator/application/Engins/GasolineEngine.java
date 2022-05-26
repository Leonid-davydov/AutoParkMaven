package by.incubator.application.Engins;

public class GasolineEngine extends CombustionEngine {
    public GasolineEngine(double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super("Gasoline", 1.1, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }
}