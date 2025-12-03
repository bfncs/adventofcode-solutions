package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day03.totalOutputJoltage;

import java.math.BigInteger;
import org.junit.jupiter.api.*;

class Day03Test {

  private static final String INPUT =
      """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
      """;

  @Test
  void testPart1() {
    assertEquals(BigInteger.valueOf(357L), totalOutputJoltage(INPUT, 2));
  }

  @Test
  void testPart2() {
    assertEquals(BigInteger.valueOf(3121910778619L), totalOutputJoltage(INPUT, 12));
  }
}
