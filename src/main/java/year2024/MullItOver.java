package year2024;

import base.AoCDay;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver extends AoCDay {

    public void solve() {
        timeMarkers[0] = Instant.now().toEpochMilli();
        List<String> lines = readResourceFile(2024, 3, false, 0);
        timeMarkers[1] = Instant.now().toEpochMilli();
        part1Answer = sumMultiplications(lines, false);
        timeMarkers[2] = Instant.now().toEpochMilli();
        part2Answer = sumMultiplications(lines, true);
        timeMarkers[3] = Instant.now().toEpochMilli();
    }

    private int sumMultiplications(List<String> lines, boolean checkToggle) {
        Pattern pattern = checkToggle ? Pattern.compile("don't\\(\\)|do\\(\\)|mul\\(\\d{1,3},\\d{1,3}\\)") : Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        boolean doMult = true;
        int total = 0;
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                if (matcher.group().equals("don't()")) {
                    doMult = false;
                } else if (matcher.group().equals("do()")) {
                    doMult = true;
                } else if (doMult) {
                    String[] nums = matcher.group().substring(4, matcher.group().length() - 1).split(",");
                    total += Integer.parseInt(nums[0], 10) * Integer.parseInt(nums[1], 10);
                }
            }
        }
        return total;
    }
}
