package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day07.*;
import static us.byteb.advent.year2024.Day07.Op.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day07Test {

  private static final String EXAMPLE_DATA =
      """
      190: 10 19
      3267: 81 40 27
      83: 17 5
      156: 15 6
      7290: 6 8 6 15
      161011: 16 10 13
      192: 17 8 14
      21037: 9 7 18 13
      292: 11 6 16 20
      """;

  @Test
  void partOneExample() {
    assertEquals(3749L, totalCalibrationResult(parseInput(EXAMPLE_DATA), Set.of(ADD, MULTIPLY)));
  }

  @Test
  void partTwoExample() {
    assertEquals(
        11387L, totalCalibrationResult(parseInput(EXAMPLE_DATA), Set.of(ADD, MULTIPLY, CONCAT)));
  }
}
