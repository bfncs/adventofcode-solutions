package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day08.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day08Test {

  private static final String EXAMPLE_DATA =
      """
      ............
      ........0...
      .....0......
      .......0....
      ....0.......
      ......A.....
      ............
      ............
      ........A...
      .........A..
      ............
      ............
      """;

  @Test
  void partOneExample() {
    assertEquals(
        Set.of(
            new Position(0, 6),
            new Position(0, 11),
            new Position(1, 3),
            new Position(2, 4),
            new Position(2, 10),
            new Position(3, 2),
            new Position(4, 9),
            new Position(5, 1),
            new Position(5, 6),
            new Position(6, 3),
            new Position(7, 0),
            new Position(7, 7),
            new Position(10, 10),
            new Position(11, 10)),
        findUniqueAntinodeLocations(EXAMPLE_DATA));
  }

  @Test
  void partTwoExample() {
    assertEquals(34L, findUniqueAntinodeLocations(EXAMPLE_DATA, true).size());
  }
}
