package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day01.*;
import static us.byteb.advent.year2016.Day01.CardinalDirection.*;

import org.junit.jupiter.api.Test;

class Day01Test {

  @Test
  void partOneExample() {
    final Location result1 = followInstructions(parseInput("R2, L3"));
    assertEquals(new Position(3, 2), result1.position());
    assertEquals(NORTH, result1.direction());
    assertEquals(5, result1.position().distanceToOrigin());

    final Location result2 = followInstructions(parseInput("R2, R2, R2"));
    assertEquals(new Position(-2, 0), result2.position());
    assertEquals(WEST, result2.direction());
    assertEquals(2, result2.position().distanceToOrigin());

    final Location result3 = followInstructions(parseInput("R5, L5, R5, R3"));
    assertEquals(new Position(2, 10), result3.position());
    assertEquals(SOUTH, result3.direction());
    assertEquals(12, result3.position().distanceToOrigin());
  }

  @Test
  void partTwoExample() {
    final Location result = followInstructionsUntilFirstRevisit(parseInput("R8, R4, R4, R8"));
    assertEquals(new Position(0, 4), result.position());
    assertEquals(NORTH, result.direction());
    assertEquals(4, result.position().distanceToOrigin());
  }
}
