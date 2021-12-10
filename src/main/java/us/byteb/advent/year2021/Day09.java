package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day09 {

  public static void main(String[] args) throws IOException {
    final List<List<Integer>> input = parseInput(readFileFromResources("year2021/day09.txt"));

    System.out.println("Part 1: " + riskLevel(findLowPoints(input)));
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

        lowPoints.add(new Point(row + 1, height));
      }
    }

    return lowPoints;
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

  record Point(int row, int height) {}
}
