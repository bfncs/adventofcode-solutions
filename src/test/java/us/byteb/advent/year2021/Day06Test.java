package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day06.evolveDays;
import static us.byteb.advent.year2021.Day06.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day06Test {

  static final String exampleData = "3,4,3,1,2";

  @Test
  void part1Example() {
    final List<Integer> startState = parseInput(exampleData);
    assertEquals(26L, evolveDays(startState, 18).size());
    assertEquals(5934L, evolveDays(startState, 80).size());
  }
}
