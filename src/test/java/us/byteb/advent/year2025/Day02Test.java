package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day02.solvePart1;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import us.byteb.advent.year2025.Day02.Range;

class Day02Test {

  static final String INPUT =
      "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";

  @Test
  void testPart1() {
    assertEquals(BigInteger.valueOf(33L), range(11, 22).countInvalidIds());
    assertEquals(BigInteger.valueOf(99L), range(95, 115).countInvalidIds());
    assertEquals(BigInteger.valueOf(1010L), range(998, 1012).countInvalidIds());
    assertEquals(BigInteger.valueOf(1227775554), solvePart1(INPUT));
  }

  private Range range(final int startInclusive, final int endInclusive) {
    return new Range(BigInteger.valueOf(startInclusive), BigInteger.valueOf(endInclusive));
  }
}
