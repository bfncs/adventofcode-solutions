package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2015.Day09.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day09Test {

  private static final String EXAMPLE_DATA =
      """
      London to Dublin = 464
      London to Belfast = 518
      Dublin to Belfast = 141
      """;

  @Test
  void partOneExample() {
    final List<DistanceDef> input = parseInput(EXAMPLE_DATA);
    assertEquals(605, shortestPath(input).totalDistance());
  }
}
