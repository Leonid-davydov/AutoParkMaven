package by.incubator.application.parsers.impl;

import by.incubator.application.entity.Orders;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.parsers.ParserBreakings;
import by.incubator.application.service.OrdersService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ParserBreakingsFromDB implements ParserBreakings {
    private static String[] details = new String[]{"Filter", "Sleeve", "Shaft", "Axis", "Candle", "Oil", "GRM", "ShRUS"};
    @Autowired
    private OrdersService ordersService;

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        Map<String, Integer> breakdowns = new HashMap<>();
        Long breakingId = generateId();
        Long vehicleId = vehicle.getId();
        String detail;
        Integer number;

        for (int i = 0; i < 2; i++) {
            detail = details[getRandomInteger(0, 7)];
            number = getRandomInteger(0, 2);

            if (number != 0) {
                breakdowns.put(detail, number);
            }
        }

        for (Map.Entry<String, Integer> entry : breakdowns.entrySet()) {
            ordersService.save(new Orders(breakingId, vehicleId, entry.getKey(), entry.getValue()));
        }

        return breakdowns;
    }

    @Override
    public void repair(Vehicles vehicle) {
    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        List<Orders> ordersList = ordersService.getAll();

        for (Orders orders : ordersList) {
            if (orders.getVehicleId() == vehicle.getId()) {
                return true;
            }
        }

        return false;
    }

    private int getRandomInteger(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    private Long generateId() {
        Long id = 1L;
        try {
            while (true) {
                ordersService.get(id++);
            }
        } catch (NoSuchElementException e) {
        } finally {
            return id;
        }
    }
}
