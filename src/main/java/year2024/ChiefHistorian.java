package year2024;

import base.AoCDay;
import base.AoCYear;
import year2024.Day1.Day1Runner;

public class ChiefHistorian extends AoCYear {
    AoCDay[] AOC_DAYS = {
            new Day1Runner(), new RedNosedReports(), new MullItOver(), new CeresSearch(), new PrintQueue(),
            new GuardGallivant(), new BridgeRepair(), new ResonantCollinearity(),
            new DiskFragmenter(), new HoofIt(), new PlutonianPebbles(), new GardenGroups()
    };

    public ChiefHistorian(int year) {
        super(year);
    }

    @Override
    public void runOneDay(int day) {
        runOneDay(day, true);
    }

    @Override
    public void runOneDay(int day, boolean printStatements) {
        AoCDay aoCDay = AOC_DAYS[day-1];
        aoCDay.run(printStatements);
    }

    @Override
    public void getSummary(int day) {
        AOC_DAYS[day-1].printSummary(day);
    }

}
