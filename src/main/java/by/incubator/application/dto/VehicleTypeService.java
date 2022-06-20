package by.incubator.application.dto;

import by.incubator.application.collections.VehicleCollection;
import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.main.Main;
import by.incubator.application.service.OrdersService;
import by.incubator.application.service.RentsService;
import by.incubator.application.service.TypesService;
import by.incubator.application.service.VehicleService;

import java.util.ArrayList;
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
    Logic logic = context.getObject(Logic.class);

    public List<VehicleDto> getVehicles() {
        return vehicleService.getAll().stream().map(vehicles -> {
            Types types = typesService.get(vehicles.getTypesId());

            return VehicleDto.builder()
                    .id(vehicles.getId())
                    .typeId(vehicles.getTypesId())
                    .typeName(types.getName())
                    .taxCoefficient(typesService.get(vehicles.getTypesId()).getCoefTaxes())
                    .color(vehicles.getColor())
                    .engineName(vehicles.getEngineType())
                    .engineTaxCoefficient(logic.getEngineTaxCoefficient(vehicles))
                    .tax(logic.getTax(vehicles, types))
                    .manufactureYear(vehicles.getManufactureYear())
                    .mileage(vehicles.getMileage())
                    .modelName(vehicles.getModel())
                    .registrationNumber(vehicles.getRegistrationNumber())
                    .tankVolume(vehicles.getVolume())
                    .weight(vehicles.getWeight())
                    .per100km(vehicles.getConsumption())
                    .maxKm(logic.getMaxKm(vehicles))
                    .income(logic.getIncome(vehicles))
                    .build();
        }).collect(Collectors.toList());
    }

    public List<VehicleTypeDto> getTypes() {
        return typesService.getAll().stream().map(types -> {

            return VehicleTypeDto.builder()
                    .id(types.getId())
                    .name(types.getName())
                    .taxCoefficient(types.getCoefTaxes())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<RentsDto> getRent(int id) {
        List<RentsDto> rentsDtoList = new ArrayList<>();

        for (Rents rents : rentsService.getAll()) {
            if (rents.getCarId() == id) {
                rentsDtoList.add(RentsDto.builder()
                        .id(rents.getId())
                        .carId(rents.getCarId())
                        .date(rents.getDate())
                        .cost(rents.getCost())
                        .build());
            }
        }



        return rentsDtoList;
    }

    public List<RentsDto> getRents() {
        return rentsService.getAll().stream().map(rents -> {

            return RentsDto.builder()
                    .id(rents.getId())
                    .carId(rents.getCarId())
                    .date(rents.getDate())
                    .cost(rents.getCost())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<OrdersDto> getOrders() {
        return ordersService.getAll().stream().map(orders -> {

            return OrdersDto.builder()
                    .id(orders.getId())
                    .vehicleId(orders.getVehicleId())
                    .defect(orders.getDefect())
                    .breakingAmount(orders.getBreakingAmount())
                    .build();
        }).collect(Collectors.toList());
    }
}
