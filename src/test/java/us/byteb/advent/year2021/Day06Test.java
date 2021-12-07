package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day06.parseInput;
import static us.byteb.advent.year2021.Day06.totalFishAfterDays;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day06Test {

  static final String exampleData = "3,4,3,1,2";

  @Test
  void part1Example() {
    final List<Integer> startState = parseInput(exampleData);
    assertEquals(26L, totalFishAfterDays(startState, 18));
    assertEquals(5934L, totalFishAfterDays(startState, 80));
  }

  @Test
  void part2Example() {
    final List<Integer> startState = parseInput(exampleData);
    assertEquals(26984457539L, totalFishAfterDays(startState, 256));
  }
}
