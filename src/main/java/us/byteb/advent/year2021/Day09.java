package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day09 {

  public static final int MAX_BASIN_HEIGHT = 9;

  public static void main(String[] args) throws IOException {
    final List<List<Integer>> input = parseInput(readFileFromResources("year2021/day09.txt"));

    System.out.println("Part 1: " + riskLevel(findLowPoints(input)));
    System.out.println("Part 2: " + productOfThreeLargestBasinSizes(input));
  }

  static List<Point> findLowPoints(final List<List<Integer>> input) {
    final List<Point> lowPoints = new ArrayList<>();
    for (int row = 0; row < input.size(); row++) {
      final List<Integer> currentRow = input.get(row);
      for (int col = 0; col < currentRow.size(); col++) {
        final int height = currentRow.get(col);

        if (row - 1 >= 0 && input.get(row - 1).get(col) <= height) continue; // up
        if (row + 1 < input.size() && input.get(row + 1).get(col) <= height) continue; // down
        if (col - 1 >= 0 && input.get(row).get(col - 1) <= height) continue; // left
        if (col + 1 < currentRow.size() && input.get(row).get(col + 1) <= height) continue; // right

        lowPoints.add(new Point(row, col, height));
      }
    }

    return lowPoints;
  }

  static Set<Set<Point>> findBasins(final List<List<Integer>> input) {
    final List<Point> lowPoints = findLowPoints(input);
    return lowPoints.stream()
        .map(lowPoint -> findBasinAround(input, Set.of(lowPoint)))
        .collect(Collectors.toSet());
  }

  private static Set<Point> findBasinAround(
      final List<List<Integer>> input, final Set<Point> knownPoints) {
    System.out.println("knownPoints = " + knownPoints);
    final Set<Point> nextPoints = new HashSet<>(knownPoints);
    for (final Point point : knownPoints) {
      final int row = point.row();
      final int col = point.col();
      if (row - 1 >= 0 && input.get(row - 1).get(col) < MAX_BASIN_HEIGHT) // up
      nextPoints.add(new Point(row - 1, col, input.get(row - 1).get(col)));
      if (row + 1 < input.size() && input.get(row + 1).get(col) < MAX_BASIN_HEIGHT) // down
      nextPoints.add(new Point(row + 1, col, input.get(row + 1).get(col)));
      if (col - 1 >= 0 && input.get(row).get(col - 1) < MAX_BASIN_HEIGHT) // left
      nextPoints.add(new Point(row, col - 1, input.get(row).get(col - 1)));
      if (col + 1 < input.get(0).size() && input.get(row).get(col + 1) < MAX_BASIN_HEIGHT) // right
      nextPoints.add(new Point(row, col + 1, input.get(row).get(col + 1)));
    }

    return nextPoints.equals(knownPoints) ? nextPoints : findBasinAround(input, nextPoints);
  }

  static long riskLevel(final List<Point> points) {
    return points.stream().mapToLong(p -> 1 + p.height()).sum();
  }

  static List<List<Integer>> parseInput(final String input) {
    return input
        .lines()
        .map(line -> line.chars().mapToObj(Character::getNumericValue).toList())
        .toList();
  }

  static long productOfThreeLargestBasinSizes(final List<List<Integer>> input) {
    return findBasins(input).stream()
        .map(ps -> (long) ps.size())
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(1L, (a, b) -> a * b);
  }

  record Point(int row, int col, int height) {}
}
