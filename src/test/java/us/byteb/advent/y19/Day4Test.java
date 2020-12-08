package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day4Test {

  @Test
  void part1Examples() {
    assertTrue(Day4.isValid1(111111));
    assertFalse(Day4.isValid1(223450));
    assertFalse(Day4.isValid1(123789));
  }

  @Test
  void part2Examples() {
    assertTrue(Day4.isValid2(112233));
    assertFalse(Day4.isValid2(123444));
    assertTrue(Day4.isValid2(111122));
  }
}
