package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day13.findEarliestPossibleDeparture;
import static us.byteb.advent.y20.Day13.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day13.Notes;

class Day13Test {

  final String INPUT = """
      939
      7,13,x,x,59,x,31,19
      """;

  @Test
  void part1ExampleParse() {
    assertEquals(new Notes(939, List.of(7, 13, 59, 31, 19)), parseInput(INPUT));
  }

  @Test
  void part1ExampleFindDeparture() {
    assertEquals(new Day13.Departure(59, 944), findEarliestPossibleDeparture(parseInput(INPUT)));
  }
}
