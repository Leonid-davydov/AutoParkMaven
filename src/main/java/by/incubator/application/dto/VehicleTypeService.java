package by.incubator.application.dto;

import by.incubator.application.checker.VehiclesChecker;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.main.Main;
import by.incubator.application.service.OrdersService;
import by.incubator.application.service.RentsService;
import by.incubator.application.service.TypesService;
import by.incubator.application.service.VehicleService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleTypeService {
    Map<Class<?>, Class<?>> interfaceToImplementation = Main.initInterfaceToImplementation();
    ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
    VehicleService vehicleService = context.getObject(VehicleService.class);
    TypesService typesService = context.getObject(TypesService.class);
    RentsService rentsService = context.getObject(RentsService.class);
    OrdersService ordersService = context.getObject(OrdersService.class);

    public List<VehicleDto> getVehicles() {
        return vehicleService.getAll().stream().map(vehicles -> {
            return VehicleDto.builder()
                    .id(vehicles.getId())
                    .typeId(vehicles.getTypesId())
                    .typeName(typesService.get(vehicles.getTypesId()).getName())
                    .taxCoefficient(typesService.get(vehicles.getTypesId()).getCoefTaxes())
                    .color(vehicles.getColor())
                    .engineName(vehicles.getEngineType())
                    .engineTaxCoefficient(logic)
                    .tax(logic)
                    .manufactureYear(vehicles.getManufactureYear())
                    .mileage(vehicles.getMileage())
                    .modelName(vehicles.getModel())
                    .registrationNumber(vehicles.getRegistrationNumber())
                    .tankVolume(vehicles.getVolume())
                    .weight(vehicles.getWeight())
                    .per100km(vehicles.getConsumption())
                    .maxKm(logic)
                    .income(logic)
                    .build();
        }).collect(Collectors.toList());
    }
}
