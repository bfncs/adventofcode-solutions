package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static us.byteb.advent.year2016.Day03.*;

import org.junit.jupiter.api.Test;

class Day03Test {

  @Test
  void partOneExample() {
    assertFalse(isValidTriangle(5, 10, 25));
  }
}
