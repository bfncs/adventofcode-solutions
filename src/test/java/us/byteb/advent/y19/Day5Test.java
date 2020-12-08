package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y19.Day5.Parameter.immediate;
import static us.byteb.advent.y19.Day5.Parameter.position;
import static us.byteb.advent.y19.Day5.parseProgram;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day5Test {

  @Test
  void parse() {
    assertEquals(
        List.of(new Day5.Mult(position(4), immediate(3), position(4))), parseProgram("1002,4,3,4"));
  }
}
