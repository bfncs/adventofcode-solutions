package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

  private static final List<Long> INPUT =
      Day9.parse(
          """
      5
      20
      15
      25
      47
      40
      62
      55
      65
      95
      102
      117
      150
      182
      127
      219
      299
      277
      309
      576
      """);

  @Test
  void part1Example() {
    assertEquals(127, Day9.findFirstInvalid(INPUT, 5));
  }

  @Test
  void part2Example() {
    final List<Long> contiguousSet = Day9.findContiguousSetWithSum(INPUT, 127);
    assertEquals(List.of(15L, 25L, 47L), contiguousSet);
    assertEquals(62, Day9.reduceToSolution(contiguousSet));
  }
}
