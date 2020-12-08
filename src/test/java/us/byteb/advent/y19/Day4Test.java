package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day4Test {

  @Test
  void part1Examples() {
    assertTrue(Day4.isValid(111111));
    assertFalse(Day4.isValid(223450));
    assertFalse(Day4.isValid(123789));
  }
}
