package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day01.*;

import org.junit.jupiter.api.Test;

class Day01Test {

  private static final String PART1_DATA =
          """
          3   4
          4   3
          2   5
          1   3
          3   9
          3   3
          """;

  @Test
  void partOneExample() {
    final Comparison data = parseInput(PART1_DATA);
    assertEquals(
        11L, totalDistance(data));
  }

}
