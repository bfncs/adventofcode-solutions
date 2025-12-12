package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.*;

class Day10Test {

  private static final String INPUT =
      """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
      """;

  @Test
  void testPart1() {
    final List<Day10.Machine> machines = Day10.parse(INPUT);
    assertEquals(2L, machines.get(0).findFewestMovesToConfigure());
    assertEquals(3L, machines.get(1).findFewestMovesToConfigure());
    assertEquals(2L, machines.get(2).findFewestMovesToConfigure());
  }
}
