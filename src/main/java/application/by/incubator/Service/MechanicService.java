package application.by.incubator.Service;

import application.by.incubator.Vehicle.Vehicle;
import application.by.incubator.Vehicle.ParserBreakingsFromFile;
import application.by.incubator.infrastrucrure.core.annotations.Autowired;

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
