package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day15.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day15Test {

  private static final String INPUT =
      """
      1163751742
      1381373672
      2136511328
      3694931569
      7463417111
      1319128137
      1359912421
      3125421639
      1293138521
      2311944581
      """;

  @Test
  void example1() {
    final List<List<Integer>> grid = parseInput(INPUT);
    assertEquals(
        new Path(
            List.of(
                new Point(1, 0),
                new Point(2, 0),
                new Point(2, 1),
                new Point(2, 2),
                new Point(2, 3),
                new Point(2, 4),
                new Point(2, 5),
                new Point(2, 6),
                new Point(3, 6),
                new Point(3, 7),
                new Point(4, 7),
                new Point(4, 8),
                new Point(5, 8),
                new Point(6, 8),
                new Point(7, 8),
                new Point(8, 8),
                new Point(8, 9),
                new Point(9, 9)),
            40),
        findPathWithLowestTotalRisk(grid));
  }
}
