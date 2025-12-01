package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day01.solvePart1;

import org.junit.jupiter.api.*;
import us.byteb.advent.year2025.Day01.Dial;

class Day01Test {

  private static final String TEST_DOCUMENT =
      """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
      """;

  @Test
  void testPart1() {
    assertEquals(95, new Dial(5).rotate("L10").getValue());
    assertEquals(3, solvePart1(TEST_DOCUMENT));
  }
}
