package us.byteb.advent.year2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

  private static final String EXAMPLE_DATA =
      """
      30373
      25512
      65332
      33549
      35390
      """;

  @Test
  void partOneExample() {
    assertEquals(21L, Day08.findVisibleTrees(EXAMPLE_DATA));
  }
}
