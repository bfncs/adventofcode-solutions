package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y19.Day5.executeProgram;
import static us.byteb.advent.y19.Day5.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day5Test {

  @Test
  void part2Example() {
    final List<Integer> equals8PositionMode = parseInput("3,9,8,9,10,9,4,9,99,-1,8");
    assertEquals(0, executeProgram(equals8PositionMode, 7));
    assertEquals(1, executeProgram(equals8PositionMode, 8));
    assertEquals(0, executeProgram(equals8PositionMode, 9));

    final List<Integer> lessThan8 = parseInput("3,9,7,9,10,9,4,9,99,-1,8");
    assertEquals(1, executeProgram(lessThan8, 7));
    assertEquals(0, executeProgram(lessThan8, 8));

    final List<Integer> equals8ImmediateMode = parseInput("3,3,1108,-1,8,3,4,3,99");
    assertEquals(0, executeProgram(equals8ImmediateMode, 7));
    assertEquals(1, executeProgram(equals8ImmediateMode, 8));
    assertEquals(0, executeProgram(equals8ImmediateMode, 9));

    final List<Integer> lessThan8ImmediateMode = parseInput("3,3,1107,-1,8,3,4,3,99");
    assertEquals(1, executeProgram(lessThan8ImmediateMode, 7));
    assertEquals(0, executeProgram(lessThan8ImmediateMode, 8));
  }
}
