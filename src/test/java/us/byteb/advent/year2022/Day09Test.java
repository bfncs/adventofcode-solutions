package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day09.*;

import org.junit.jupiter.api.Test;

class Day09Test {

  private static final String PART_ONE_EXAMPLE_DATA =
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

  private static final String PART_TWO_EXAMPLE_DATA =
      """
      R 5
      U 8
      L 8
      D 3
      R 17
      D 10
      L 25
      U 20
      """;

  @Test
  void partOneExample() {
    assertEquals(13L, countPositionsVisitedByTail(PART_ONE_EXAMPLE_DATA, 2));
  }

  @Test
  void partTwoExample() {
    assertEquals(1L, countPositionsVisitedByTail(PART_ONE_EXAMPLE_DATA, 10));
    assertEquals(36L, countPositionsVisitedByTail(PART_TWO_EXAMPLE_DATA, 10));
  }
}
