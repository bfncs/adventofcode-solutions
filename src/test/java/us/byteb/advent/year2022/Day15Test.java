package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day15.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day15Test {

  private static final String EXAMPLE_DATA = readFileFromResources("year2022/day15.txt");

  @Test
  void partOneExample() {
    final Set<Position> result = positionsWithoutBeacon(EXAMPLE_DATA, 10);
    assertEquals(26L, result.size());
  }
}
