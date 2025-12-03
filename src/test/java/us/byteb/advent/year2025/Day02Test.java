package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day02.*;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import us.byteb.advent.year2025.Day02.Range;

class Day02Test {

  static final String INPUT =
      "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";

  @Test
  void testPart1() {
    assertEquals(BigInteger.valueOf(33L), range(11L, 22L).sumInvalidIds1());
    assertEquals(BigInteger.valueOf(99L), range(95L, 115L).sumInvalidIds1());
    assertEquals(BigInteger.valueOf(1010L), range(998L, 1012L).sumInvalidIds1());

    assertEquals(BigInteger.valueOf(1227775554), solvePart1(INPUT));
  }

  @Test
  void testPart2() {
    assertEquals(BigInteger.valueOf(33L), range(11L, 22L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(210L), range(95L, 115L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(2009L), range(998L, 1012L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(1188511885L), range(1188511880L, 1188511890L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(222222L), range(222220L, 222224L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(0L), range(1698522L, 1698528L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(446446L), range(446443L, 446449L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(38593859L), range(38593856L, 38593862L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(565656L), range(565653L, 565659L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(824824824L), range(824824821L, 824824827L).sumInvalidIds2());
    assertEquals(BigInteger.valueOf(2121212121L), range(2121212118L, 2121212124L).sumInvalidIds2());

    assertEquals(BigInteger.valueOf(4174379265L), solvePart2(INPUT));
  }

  private Range range(final long startInclusive, final long endInclusive) {
    return new Range(BigInteger.valueOf(startInclusive), BigInteger.valueOf(endInclusive));
  }
}
