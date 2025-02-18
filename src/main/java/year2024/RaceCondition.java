package year2024;

import base.AoCDay;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.util.*;

public class RaceCondition extends AoCDay {

    public final int MIN_COST_SAVINGS = 100;

    public void solve() {
        timeMarkers[0] = Instant.now().toEpochMilli();
        char[][] grid = convertToCharGrid(readResourceFile(2024, 20, false, 0));
        Pair<Integer, Integer> startPos = findCharInGrid(grid, 'S');
        Pair<Integer, Integer> endPos = findCharInGrid(grid, 'E');
        Pair<Map<Pair<Integer, Integer>, Integer>, Map<Pair<Integer, Integer>, Pair<Integer, Integer>>> searchOutput = aStarSearchReturningAllNodeCosts(grid, startPos, endPos);
        List<Pair<Integer, Integer>> optimalPath = getCellLocations(searchOutput.getRight(), endPos);
        timeMarkers[1] = Instant.now().toEpochMilli();
        part1Answer = countCheats(searchOutput.getLeft(), optimalPath,2);
        timeMarkers[2] = Instant.now().toEpochMilli();
        part2Answer = countCheats(searchOutput.getLeft(), optimalPath,20);
        timeMarkers[3] = Instant.now().toEpochMilli();
    }

    private int countCheats(Map<Pair<Integer, Integer>, Integer> nodeCosts, List<Pair<Integer, Integer>> optimalPath, int cheatDistance) {
        int count = 0;
        for (Pair<Integer, Integer> point : optimalPath) {
            for (int i = -cheatDistance; i <= cheatDistance; i++) {
                for (int j = -(cheatDistance - Math.abs(i)); j <= cheatDistance - Math.abs(i); j++) {
                    Pair<Integer, Integer> newPoint = Pair.of(point.getLeft() + i, point.getRight() + j);
                    if (nodeCosts.containsKey(newPoint)) {
                        if (nodeCosts.get(newPoint) - nodeCosts.get(point) >= getManhattanDistance(point, newPoint) + MIN_COST_SAVINGS) count++;
                    }
                }
            }
        }
        return count;
    }

    private Pair<Map<Pair<Integer, Integer>, Integer>, Map<Pair<Integer, Integer>, Pair<Integer, Integer>>> aStarSearchReturningAllNodeCosts(char[][] grid, Pair<Integer, Integer> startPos, Pair<Integer, Integer> endPos) {
        Map<Pair<Integer, Integer>, Integer> nodeCosts = new HashMap<>();
        nodeCosts.put(startPos, 0);
        PriorityQueue<Pair<Integer, Integer>> frontier = new PriorityQueue<>(Comparator.comparingInt(o -> nodeCosts.get(o) + getManhattanDistance(o, endPos)));
        frontier.add(startPos);
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> prevNode = new HashMap<>();

        while (!frontier.isEmpty()) {
            Pair<Integer, Integer> current = frontier.poll();

            if (current.equals(endPos)) return Pair.of(nodeCosts, prevNode);
            for (Pair<Integer, Integer> next : getNeighbors(current, grid)) {
                int newCost = nodeCosts.get(current) + 1;
                if (!nodeCosts.containsKey(next) || newCost < nodeCosts.get(next)) {
                    nodeCosts.put(next, newCost);
                    frontier.add(next);
                    prevNode.put(next, current);
                }
            }
        }
        return Pair.of(Map.of(), Map.of());
    }
}
