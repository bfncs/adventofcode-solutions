package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import us.byteb.advent.year2015.Day02.Box;

class Day02Test {

  @Test
  void example1() {
    assertEquals(58L, Box.of("2x3x4").requiredWrappingPaper());
    assertEquals(43L, Box.of("1x1x10").requiredWrappingPaper());
  }

  @Test
  void example2() {
    assertEquals(34L, Box.of("2x3x4").requiredRibbonLength());
    assertEquals(14L, Box.of("1x1x10").requiredRibbonLength());
  }
}
