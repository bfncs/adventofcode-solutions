package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day12.*;

import org.junit.jupiter.api.Test;

class Day12Test {

  final String EXAMPLE_DATA =
      """
      Sabqponm
      abcryxxl
      accszExk
      acctuvwj
      abdefghi
      """;

  @Test
  void partOneExample() {
    assertEquals(31, shortestPath(EXAMPLE_DATA).size());
  }
}
