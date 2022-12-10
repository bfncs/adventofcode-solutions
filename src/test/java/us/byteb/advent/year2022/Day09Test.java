package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day09.*;

import org.junit.jupiter.api.Test;

class Day09Test {

  private static final String EXAMPLE_DATA =
      """
      R 4
      U 4
      L 3
      D 1
      R 4
      D 1
      L 5
      R 2
      """;

  @Test
  void partOneExample() {
    assertEquals(13L, countPositionsVisitedByTail(EXAMPLE_DATA));
  }
}
