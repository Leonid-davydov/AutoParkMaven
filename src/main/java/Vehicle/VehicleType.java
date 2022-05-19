package Vehicle;

public class VehicleType {
    private int id;
    private String name;
    private double coefficient;

    VehicleType() {}

    public VehicleType( String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }
    public VehicleType(int id, String name, double coefficient) {
        this(name, coefficient);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void display() {
        System.out.println("typeName = " + name);
        System.out.println("taxCoefficient = " + coefficient);
    }

    public String getString() {
        return (name + ", \"" + coefficient + "\"");
    }

    @Override
    public String toString() {
        return (id + " " + name + ", \"" + coefficient + "\"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
