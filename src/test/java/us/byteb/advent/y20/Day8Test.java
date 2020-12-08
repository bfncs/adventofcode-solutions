package us.byteb.advent.y20;

import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day8.Acc;
import us.byteb.advent.y20.Day8.Jmp;
import us.byteb.advent.y20.Day8.Nop;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day8.*;

class Day8Test {

  private static final String EXAMPLE_INPUT = """
      nop +0
      acc +1
      jmp +4
      acc +3
      jmp -3
      acc -99
      acc +1
      jmp -4
      acc +6
      """;

  @Test
  void parse() {
    assertEquals(List.of(
        new Nop(0),
        new Acc(1),
        new Jmp(4),
        new Acc(3),
        new Jmp(-3),
        new Acc(-99),
        new Acc(1),
        new Jmp(-4),
        new Acc(6)
    ), parseProgram(EXAMPLE_INPUT));
  }

  @Test
  void solvePart1Example() {
    assertEquals(5, executeUntilFirstInstructionWouldBeExecutedTwice(parseProgram(EXAMPLE_INPUT)));
  }

  @Test
  void solvePart2Example() {
    assertEquals(8, executeUntilTerminatesWithFix(parseProgram(EXAMPLE_INPUT)));
  }
}
