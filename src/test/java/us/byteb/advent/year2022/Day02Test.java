package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day02.*;

import org.junit.jupiter.api.Test;

class Day02Test {

  private static final String part1ExampleDate = """
      A Y
      B X
      C Z
      """;

  @Test
  void partOneExample() {
    assertEquals(15L, totalScore(parseInput(part1ExampleDate, STRATEGY_PART1)));
  }

  @Test
  void partTwoExample() {
    assertEquals(12L, totalScore(parseInput(part1ExampleDate, STRATEGY_PART2)));
  }
}
