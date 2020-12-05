package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y19.Day1.calcRequiredFuel;
import static us.byteb.advent.y19.Day1.calcRequiredFuelWithFuelForFuel;

import org.junit.jupiter.api.Test;

class Day1Test {

  @Test
  void testCalcRequiredFuel() {
    assertEquals(2, calcRequiredFuel(12));
    assertEquals(2, calcRequiredFuel(14));
    assertEquals(654, calcRequiredFuel(1969));
    assertEquals(33583, calcRequiredFuel(100756));
  }

  @Test
  void testCalcRequiredFuelWithFuelForFuel() {
    assertEquals(2, calcRequiredFuelWithFuelForFuel(12));
    assertEquals(966, calcRequiredFuelWithFuelForFuel(1969));
    assertEquals(50346, calcRequiredFuelWithFuelForFuel(100756));
  }
}
