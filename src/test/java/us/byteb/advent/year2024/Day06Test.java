package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day06.*;

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
}
