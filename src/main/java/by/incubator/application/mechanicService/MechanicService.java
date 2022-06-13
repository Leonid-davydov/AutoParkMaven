package by.incubator.application.mechanicService;

import by.incubator.application.entity.Vehicles;
import by.incubator.application.parsers.ParserBreakings;
import by.incubator.application.parsers.impl.ParserBreakingsFromFile;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;

import java.util.Map;

public class MechanicService implements Fixer {
    private static String[] details = {"Filter", "Sleeve", "Shaft", "Axis", "Candle", "Oil", "GRM", "ShRUS"};
    @Autowired
    private ParserBreakings parser;

    public MechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        return parser.detectBreaking(vehicle);
    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        return parser.isBroken(vehicle);
    }

    @Override
    public void repair(Vehicles vehicle) {
        parser.repair(vehicle);
    }

    public ParserBreakings getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsFromFile parser) {
        this.parser = parser;
    }
}
