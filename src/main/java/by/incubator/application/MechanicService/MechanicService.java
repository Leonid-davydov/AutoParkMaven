package by.incubator.application.MechanicService;

import by.incubator.application.Vehicle.Vehicle;
import by.incubator.application.Vehicle.ParserBreakingsFromFile;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;

import java.util.Map;

public class MechanicService implements Fixer {
    private static String[] details = {"Filter", "Sleeve", "Shaft", "Axis", "Candle", "Oil", "GRM", "ShRUS"};
    @Autowired
    private ParserBreakingsFromFile parser;

    public MechanicService() {}

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        return parser.detectBreaking(vehicle);
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        return parser.isBroken(vehicle);
    }

    @Override
    public void repair(Vehicle vehicle) {
        parser.repair(vehicle);
    }

    public ParserBreakingsFromFile getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsFromFile parser) {
        this.parser = parser;
    }
}
