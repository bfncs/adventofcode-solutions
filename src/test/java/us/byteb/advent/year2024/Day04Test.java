package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day04.*;

import org.junit.jupiter.api.Test;

class Day04Test {

  private static final String EXAMPLE_PART1 =
      """
      MMMSXXMASM
      MSAMXMSMSA
      AMXSXMAAMM
      MSAMASMSMX
      XMASAMXAMM
      XXAMMXXAMA
      SMSMSASXSS
      SAXAMASAAA
      MAMMMXMMMM
      MXMXAXMASX
      """;

  @Test
  void partOneExample() {
    assertEquals(18, searchWord(EXAMPLE_PART1));
  }
}
