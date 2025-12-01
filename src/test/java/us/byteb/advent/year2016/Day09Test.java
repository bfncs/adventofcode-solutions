package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals(expected, Day09.decompress(input));
  }
}
