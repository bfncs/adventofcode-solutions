package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day09.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2021.Day09.Point;

class Day09Test {

  private static final String exampleInput =
      """
      2199943210
      3987894921
      9856789892
      8767896789
      9899965678
      """;

  @Test
  void part1Example() {
    final List<Point> lowPoints = findLowPoints(parseInput(exampleInput));
    assertEquals(
        List.of(new Point(1, 1), new Point(1, 0), new Point(3, 5), new Point(5, 5)), lowPoints);
    assertEquals(15L, riskLevel(lowPoints));
  }
}
