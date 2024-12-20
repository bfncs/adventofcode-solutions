package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day02.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day02Test {

  private static final String EXAMPLE_DATA =
      """
      7 6 4 2 1
      1 2 7 8 9
      9 7 6 2 1
      1 3 2 4 5
      8 6 4 4 1
      1 3 6 7 9
      """;

  @Test
  void partOneExample() {
    final List<List<Long>> data = parseInput(EXAMPLE_DATA);
    final List<Boolean> result = data.stream().map(Day02::isSafe).toList();
    assertEquals(List.of(true, false, false, false, false, true), result);
  }

  @Test
  void partTwoExample() {
    final List<List<Long>> data = parseInput(EXAMPLE_DATA);
    final List<Boolean> result = data.stream().map(Day02::isSafeWithProblemDampener).toList();
    assertEquals(List.of(true, false, false, true, true, true), result);
  }
}
