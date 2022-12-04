package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day04.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day04Test {

  private static final String PART1_EXAMPLE_DATA =
      """
      2-4,6-8
      2-3,4-5
      5-7,7-9
      2-8,3-7
      6-6,4-6
      2-6,4-8
      """;

  @Test
  void partOneExample() {
    final List<Pair> backpacks = parseInput(PART1_EXAMPLE_DATA);

    assertEquals(2L, numberOfPairsWhereOneContainsTheOther(backpacks));
  }
}
