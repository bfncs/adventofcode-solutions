package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day12.Direction.*;
import static us.byteb.advent.y20.Day12.move;
import static us.byteb.advent.y20.Day12.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day12.Instruction;
import us.byteb.advent.y20.Day12.Position;

class Day12Test {

  @Test
  void part1Example() {
    final List<Instruction> instructions =
        parseInput("""
        F10
        N3
        F7
        R90
        F11
        """);
    assertEquals(new Position(17, 8, SOUTH), move(instructions));
  }

  @Test
  void directionTurn() {
    assertEquals(EAST, NORTH.turn(90));
    assertEquals(SOUTH, NORTH.turn(180));
    assertEquals(WEST, NORTH.turn(270));
    assertEquals(NORTH, NORTH.turn(360));
    assertEquals(NORTH, NORTH.turn(720));
    assertEquals(WEST, NORTH.turn(-90));
    assertEquals(SOUTH, NORTH.turn(-180));
    assertEquals(EAST, NORTH.turn(-270));
    assertEquals(NORTH, NORTH.turn(-360));
    assertEquals(NORTH, NORTH.turn(-720));
  }
}
