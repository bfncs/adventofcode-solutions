package us.byteb.advent.year2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day08.findHighestScenicScore;
import static us.byteb.advent.year2022.Day08.findVisibleTrees;

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
    assertEquals(21L, findVisibleTrees(EXAMPLE_DATA));
  }

  @Test
  void partTwoExample() {
    assertEquals(8L, findHighestScenicScore(EXAMPLE_DATA));
  }
}
