package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day06.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day06Test {

  private static final String EXAMPLE_DATA =
      """
      ....#.....
      .........#
      ..........
      ..#.......
      .......#..
      ..........
      .#..^.....
      ........#.
      #.........
      ......#...
      """;

  @Test
  void partOneExample() {
    assertEquals(41L, positionBeforeLeavingMap(EXAMPLE_DATA));
  }

  @Test
  void partTwoExample() {
    assertEquals(
        Set.of(
            new Position(6, 3),
            new Position(7, 6),
            new Position(7, 7),
            new Position(8, 1),
            new Position(8, 3),
            new Position(9, 7)),
        positionsForLoop(EXAMPLE_DATA));
  }
}
