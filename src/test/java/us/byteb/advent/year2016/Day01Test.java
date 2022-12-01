package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day01.*;
import static us.byteb.advent.year2016.Day01.CardinalDirection.*;

import org.junit.jupiter.api.Test;

class Day01Test {

  @Test
  void partOneExample() {
    final Position result1 = followInstructions(parseInput("R2, L3"));
    assertEquals(new Position(3, 2, NORTH), result1);
    assertEquals(5, result1.distanceToOrigin());

    final Position result2 = followInstructions(parseInput("R2, R2, R2"));
    assertEquals(new Position(-2, 0, WEST), result2);
    assertEquals(2, result2.distanceToOrigin());

    final Position result3 = followInstructions(parseInput("R5, L5, R5, R3"));
    assertEquals(new Position(2, 10, SOUTH), result3);
    assertEquals(12, result3.distanceToOrigin());
  }
}
