package by.incubator.application.engins;

public abstract class AbstractEngine implements Startable {
    private String engineType;
    private double taxCoefficient;

    AbstractEngine (String engineType, double taxCoefficient) {
        this.engineType = engineType;
        this.taxCoefficient = taxCoefficient;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    public String toString() {
        return engineType + ", " + taxCoefficient;
    }
}
