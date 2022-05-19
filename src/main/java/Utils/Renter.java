package Utils;

import Exceptions.DefectedVehicleException;
import Service.MechanicService;
import Vehicle.Vehicle;

public class Renter {
    public static void rent(Vehicle v){
        try{
            if(new MechanicService().isBroken(v))
                throw new DefectedVehicleException("Vehicle is defected");
            else
                System.out.println("Car is rented successfully");
        } catch (DefectedVehicleException e) {
            e.printStackTrace();
        }
    }
}
