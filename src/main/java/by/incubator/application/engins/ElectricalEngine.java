package by.incubator.application.engins;

public class ElectricalEngine extends AbstractEngine {

    private double batterySize;
    private double electricityConsumption;

    public ElectricalEngine( double batterySize, double electricityConsumption) {
        super("Electrical", 0.1);
        this.batterySize = batterySize;
        this.electricityConsumption = electricityConsumption;
    }

    @Override
    public double getTaxPerMonth() {
        return 0.1;
    }

    @Override
    public double getMaxKilometers() {
        return batterySize / electricityConsumption;
    }

    public double getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(double batterySize) {
        this.batterySize = batterySize;
    }

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }
}
