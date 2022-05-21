package application.by.incubator.Vehicle;

import application.by.incubator.Engins.Startable;
import application.by.incubator.Exceptions.NotVehicleException;
import application.by.incubator.Rent.Rent;
import application.by.incubator.Service.TechnicalSpecialist;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Comparable {
    private int id;
    private final VehicleType vehicleType;
    private final String modelName;
    private String registrationNumber;
    private int weight;
    private final int manufactureYear;
    private int mileage;
    private Color color;
    private int volume;
    Startable engine;
    private List<Rent> history;


    Vehicle() {
        vehicleType = null;
        modelName = null;
        manufactureYear = 0;
    }

    Vehicle(VehicleType vehicleType, String modelName, String registrationNumber, int weight,
            int manufactureYear, int mileage, Color color, Startable engine) {
        this.vehicleType = vehicleType;
        this.modelName = modelName;
        this.manufactureYear = manufactureYear;
        this.engine = engine;
        history = new ArrayList<>();
        try {
            setRegistrationNumber(registrationNumber);
            setWeight(weight);
            setMileage(mileage);
            setColor(color);
        } catch (NotVehicleException e) {
            System.out.println(e.getMessage());
        }
    }

    public Vehicle(int id, VehicleType vehicleType, String modelName, String registrationNumber, int weight,
                   int manufactureYear, int mileage, Color color, Startable engine) {
        this(vehicleType, modelName, registrationNumber, weight,
        manufactureYear, mileage, color, engine);
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) throws NotVehicleException {
        if (TechnicalSpecialist.validateRegistrationNumber(registrationNumber))
            this.registrationNumber = registrationNumber;
        else
            throw new NotVehicleException("Incorrect registration number: " + registrationNumber);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) throws NotVehicleException {
        if (TechnicalSpecialist.validateWeight(weight))
            this.weight = weight;
        else
            throw new NotVehicleException("Incorrect weight: " + weight);
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) throws NotVehicleException {
        if (TechnicalSpecialist.validateMileage(mileage))
            this.mileage = mileage;
        else
            throw new NotVehicleException("Incorrect mileage: " + mileage);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) throws NotVehicleException {
        if (TechnicalSpecialist.validateColor(color))
            this.color = color;
        else
            throw new NotVehicleException("Incorrect color: " + color);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }

    public Startable getEngine() {
        return engine;
    }

    public double getCalcTaxPerMonth() {
        return ((weight * 0.0013d) + (vehicleType.getCoefficient() * engine.getTaxCoefficient()* 30) + 5);
    }

    @Override
    public String toString() {
        return vehicleType.getString() + ", " + modelName + ", " + registrationNumber + ", "
                + weight + ", " + manufactureYear + ", " + mileage + ", " + color + ", " + volume
                + ", " + engine.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehicle))
            return false;
        Vehicle secondVehicle = (Vehicle) o;

        return modelName.equals(secondVehicle.getModelName()) && vehicleType.equals(secondVehicle.getVehicleType());
    }

    @Override
    public int hashCode() {
        return modelName.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        Vehicle secondVehicle = (Vehicle) o;

        if (manufactureYear != secondVehicle.getManufactureYear()) {
            return manufactureYear - secondVehicle.getManufactureYear();
        } else {
            return mileage - secondVehicle.getMileage();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rent> getHistory() {
        return history;
    }

    public void setHistory(List<Rent> history) {
        this.history = history;
    }

    public double getTotalIncome() {
        double sum = 0.0;
        for (Rent rent : history) {
            sum += rent.getPrice();
        }
        return sum;
    }

    public double getTotalProfit() {
        return getTotalIncome() - getCalcTaxPerMonth();
    }
}
