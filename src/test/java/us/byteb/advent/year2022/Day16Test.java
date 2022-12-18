package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day16.maxReleasablePressure;

import org.junit.jupiter.api.Test;

class Day16Test {

  private static final String EXAMPLE_DATA = readFileFromResources("year2022/day16.txt");

  @Test
  void partOneExample() {
    assertEquals(1651L, maxReleasablePressure(EXAMPLE_DATA));
  }
}
