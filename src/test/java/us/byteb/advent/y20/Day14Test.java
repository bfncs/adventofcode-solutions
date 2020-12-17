package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day14.*;
import static us.byteb.advent.y20.Day14.parseInput;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day14.Instruction.Mask;
import us.byteb.advent.y20.Day14.Instruction.Mem;

class Day14Test {

  final String INPUT =
      """
      mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
      mem[8] = 11
      mem[7] = 101
      mem[8] = 0
      """;

  @Test
  void parseExample() {
    assertEquals(
        List.of(new Mask(0b10, 0b1000000), new Mem(8, 11), new Mem(7, 101), new Mem(8, 0)),
        parseInput(INPUT));
  }

  @Test
  void parseMask() {
    assertEquals(new Mask(0b10, 0b1000000), Mask.of("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"));
  }

  @Test
  void part1Execute() {
    assertEquals(Map.of(7L, 101L, 8L, 64L), execute(parseInput(INPUT)));
  }
}
