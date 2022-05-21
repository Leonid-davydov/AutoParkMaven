package application.by.incubator.Exceptions;

public class DefectedVehicleException extends Throwable {
    DefectedVehicleException() {}

    DefectedVehicleException(String massage) {
        super(massage);
    }
}
