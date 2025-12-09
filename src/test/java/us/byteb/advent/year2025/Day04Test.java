package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.*;
import us.byteb.advent.year2025.Day04.Grid;
import us.byteb.advent.year2025.Day04.Point;

class Day04Test {

  final String INPUT =
      """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
      """;

  @Test
  void testPart1() {
    assertEquals(
        Set.of(
            new Point(0, 2),
            new Point(0, 3),
            new Point(0, 5),
            new Point(0, 6),
            new Point(0, 8),
            new Point(1, 0),
            new Point(2, 6),
            new Point(4, 0),
            new Point(4, 9),
            new Point(7, 0),
            new Point(9, 0),
            new Point(9, 2),
            new Point(9, 8)),
        Grid.parse(INPUT).findAccessiblePoints());
  }
}
