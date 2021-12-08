package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day07.findCheapestAlignment;
import static us.byteb.advent.year2021.Day07.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day07Test {

  private static final List<Integer> EXAMPLE_INPUT = parseInput("16,1,2,0,4,2,7,1,2,14");

  @Test
  void part1Example() {
    assertEquals(37, findCheapestAlignment(EXAMPLE_INPUT));
  }
}
