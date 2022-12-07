package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day02.findCode;

import org.junit.jupiter.api.Test;

class Day02Test {

  private static final String PART1_DATA =
      """
      ULL
      RRDDD
      LURDL
      UUUUD
      """;

  @Test
  void partOneExample() {
    assertEquals("1985", findCode(PART1_DATA));
  }
}
