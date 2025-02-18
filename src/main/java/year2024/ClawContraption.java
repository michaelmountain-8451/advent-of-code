package year2024;

import base.AoCDay;
import org.apache.commons.lang3.tuple.Pair;
import year2024.Day13.Machine;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class ClawContraption extends AoCDay {

    public void solve() {
        timeMarkers[0] = Instant.now().toEpochMilli();
        List<String> lines = readResourceFile(2024, 13, false, 0);
        List<Machine> machines = getMachines(lines);
        timeMarkers[1] = Instant.now().toEpochMilli();
        part1Answer = countTokens(machines);
        timeMarkers[2] = Instant.now().toEpochMilli();
        part2Answer = solvePt2(machines);
        timeMarkers[3] = Instant.now().toEpochMilli();
    }


    private long solvePt2(List<Machine> machines) {
        for (Machine machine: machines) {
            Pair<Long, Long> prize = machine.getPrize();
            machine.setPrize(Pair.of(prize.getLeft() + 10000000000000L, prize.getRight() + 10000000000000L));
        }
        return countTokens(machines);
    }

    private long countTokens(List<Machine> machines) {
        long tokenCount = 0;
        for (Machine machine : machines) {
            // aXInc*aCount + bXInc*bCount = prizeX
            // aYInc*aCount + bYInc*bCount = prizeY
            // Cramer's Rule
            long aCount = Math.round(
                    (double)(machine.getPrize().getLeft() * machine.getbBtn().getRight() - machine.getbBtn().getLeft() * machine.getPrize().getRight())
                            / (machine.getaBtn().getLeft() * machine.getbBtn().getRight() - machine.getaBtn().getRight() * machine.getbBtn().getLeft()));
            long bCount = Math.round(
                    (double)(machine.getaBtn().getLeft() * machine.getPrize().getRight() - machine.getPrize().getLeft() * machine.getaBtn().getRight())
                            / (machine.getaBtn().getLeft() * machine.getbBtn().getRight() - machine.getaBtn().getRight() * machine.getbBtn().getLeft()));
            if (Pair.of(machine.getaBtn().getLeft() * aCount + machine.getbBtn().getLeft() * bCount,
                    machine.getaBtn().getRight() * aCount + machine.getbBtn().getRight() * bCount).equals(machine.getPrize())) {
                tokenCount += 3 * aCount + bCount;
            }
        }
        return tokenCount;

    }

    private List<Machine> getMachines(List<String> lines) {
        List<Machine> result = new LinkedList<>();
        Machine machine = new Machine();
        for (String line : lines) {
            if (line.startsWith("Button A")) {
                machine = new Machine();
                machine.setaBtn(Pair.of(
                        Integer.parseInt(line.split("\\+")[1].split(",")[0], 10),
                        Integer.parseInt(line.split("\\+")[2], 10)
                ));
            } else if (line.startsWith("Button B")) {
                machine.setbBtn(Pair.of(
                        Integer.parseInt(line.split("\\+")[1].split(",")[0], 10),
                        Integer.parseInt(line.split("\\+")[2], 10)
                ));
            } else if (line.startsWith("Prize")) {
                machine.setPrize(Pair.of(
                        Long.parseLong(line.split("=")[1].split(",")[0], 10),
                        Long.parseLong(line.split("=")[2], 10)
                ));
                result.add(machine);
            }
        }
        return result;
    }
}
