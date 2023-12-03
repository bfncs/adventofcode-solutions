package us.byteb.advent.year2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2023.Day03.*;

import org.junit.jupiter.api.Test;

class Day03Test {

  private static final String part1ExampleDate =
      """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
            """;

  @Test
  void partOneExample() {
    assertEquals(4361L, sumOfPartNumbers(part1ExampleDate));
  }
}
