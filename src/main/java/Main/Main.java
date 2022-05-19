package Main;

import Collections.VehicleCollection;
import Service.MechanicService;
import Utils.Mechanic;
import Vehicle.Vehicle;

import static Utils.Renter.rent;

public class Main {
    public static void main(String[] args) {
        VehicleCollection vehicleCollection = new VehicleCollection("types", "vehicles", "rents");
        MechanicService mechanicService = new MechanicService();

        Mechanic.detectAndRepairAll(vehicleCollection, mechanicService);
        Mechanic.detectBreakingAll(vehicleCollection, mechanicService);
        System.out.println(Mechanic.findMost(vehicleCollection, mechanicService));

        rent(vehicleCollection.getVCollection().get(0));
    }
}







