package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day09.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

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
    assertEquals(List.of(p(0, 1, 1), p(0, 9, 0), p(2, 2, 5), p(4, 6, 5)), lowPoints);
    assertEquals(15L, riskLevel(lowPoints));
  }

  @Test
  void part2Example() {
    final Set<Set<Point>> basins = findBasins(parseInput(exampleInput));
    assertEquals(
        Set.of(
            Set.of(p(0, 0, 2), p(0, 1, 1), p(1, 0, 3)),
            Set.of(
                p(0, 5, 4),
                p(0, 6, 3),
                p(0, 7, 2),
                p(0, 8, 1),
                p(0, 9, 0),
                p(1, 6, 4),
                p(1, 8, 2),
                p(1, 9, 1),
                p(2, 9, 2)),
            Set.of(
                p(1, 2, 8),
                p(1, 3, 7),
                p(1, 4, 8),
                p(2, 1, 8),
                p(2, 2, 5),
                p(2, 3, 6),
                p(2, 4, 7),
                p(2, 5, 8),
                p(3, 0, 8),
                p(3, 1, 7),
                p(3, 2, 6),
                p(3, 3, 7),
                p(3, 4, 8),
                p(4, 1, 8)),
            Set.of(
                p(2, 7, 8),
                p(3, 6, 6),
                p(3, 7, 7),
                p(3, 8, 8),
                p(4, 5, 6),
                p(4, 6, 5),
                p(4, 7, 6),
                p(4, 8, 7),
                p(4, 9, 8))),
        basins);

    assertEquals(1134L, productOfThreeLargestBasinSizes(parseInput(exampleInput)));
    ;
  }

  private static Point p(final int i, final int i2, final int i3) {
    return new Point(i, i2, i3);
  }
}
