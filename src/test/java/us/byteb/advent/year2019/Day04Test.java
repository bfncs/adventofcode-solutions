package us.byteb.advent.year2019;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day04Test {

  @Test
  void part1Examples() {
    assertTrue(Day04.isValid1(111111));
    assertFalse(Day04.isValid1(223450));
    assertFalse(Day04.isValid1(123789));
  }

  @Test
  void part2Examples() {
    assertTrue(Day04.isValid2(112233));
    assertFalse(Day04.isValid2(123444));
    assertTrue(Day04.isValid2(111122));
  }
}
