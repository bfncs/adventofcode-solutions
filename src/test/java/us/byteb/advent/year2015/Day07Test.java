package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2015.Day07.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2015.Day07.Operand.Wire;

class Day07Test {

  private static final String EXAMPLE_INPUT =
      """
        123 -> x
        456 -> y
        x AND y -> d
        x OR y -> e
        x LSHIFT 2 -> f
        y RSHIFT 2 -> g
        NOT x -> h
        NOT y -> i
        """;

  @Test
  void example1() {
    final List<Instruction> instructions = parseInput(EXAMPLE_INPUT);

    assertEquals(72, evaluateWire(instructions, new Wire("d")));
    assertEquals(507, evaluateWire(instructions, new Wire("e")));
    assertEquals(492, evaluateWire(instructions, new Wire("f")));
    assertEquals(114, evaluateWire(instructions, new Wire("g")));
    assertEquals(65412, evaluateWire(instructions, new Wire("h")));
    assertEquals(65079, evaluateWire(instructions, new Wire("i")));
    assertEquals(123, evaluateWire(instructions, new Wire("x")));
    assertEquals(456, evaluateWire(instructions, new Wire("y")));
  }
}
