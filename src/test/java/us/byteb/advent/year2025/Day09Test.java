package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day09.*;

import org.junit.jupiter.api.*;

class Day09Test {

  private static final String INPUT =
      """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3
      """;

  @Test
  void testPart1() {
    assertEquals(50L, largestArea(parse(INPUT)));
  }

  @Test
  void testPart2() {
    assertEquals(24L, largestAreaRedGreen(parse(INPUT)));
  }
}
