package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2016.Day09.decompress;
import static us.byteb.advent.year2016.Day09.decompress2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day09Test {

  @ParameterizedTest
  @CsvSource({
    "ADVENT,ADVENT",
    "A(1x5)BC,ABBBBBC",
    "(3x3)XYZ,XYZXYZXYZ",
    "A(2x2)BCD(2x2)EFG,ABCBCDEFEFG",
    "(6x1)(1x3)A,(1x3)A",
    "X(8x2)(3x3)ABCY,X(3x3)ABC(3x3)ABCY"
  })
  void testPart1(final String input, final String expected) {
    assertEquals(expected, decompress(input));
  }

  @Test
  void testPart2() {
    assertEquals("XYZXYZXYZ".length(), decompress2("(3x3)XYZ"));
    assertEquals("XABCABCABCABCABCABCY".length(), decompress2("X(8x2)(3x3)ABCY"));
    assertEquals(241920, decompress2("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
    assertEquals(445, decompress2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
  }
}
