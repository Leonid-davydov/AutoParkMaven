package Exceptions;

public class DefectedVehicleException extends Throwable {
    DefectedVehicleException() {}

    public DefectedVehicleException(String massage) {
        super(massage);
    }
}
