package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day06.grandTotal;
import static us.byteb.advent.year2025.Day06.parse;

import java.math.BigInteger;
import org.junit.jupiter.api.*;

class Day06Test {

  static final String INPUT =
      """
      123 328  51 64\s
       45 64  387 23\s
        6 98  215 314
      *   +   *   + \s
      """;

  @Test
  void testPart1() {
    assertEquals(BigInteger.valueOf(4277556L), grandTotal(parse(INPUT)));
  }
}
