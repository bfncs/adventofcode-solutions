package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2020.Day14.*;
import static us.byteb.advent.year2020.Day14.parseInput;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2020.Day14.Instruction.Mask;
import us.byteb.advent.year2020.Day14.Instruction.Mem;

class Day14Test {

  @Test
  void parseExample() {
    assertEquals(
        List.of(new Mask(0b10, 0b1000000), new Mem(8, 11), new Mem(7, 101), new Mem(8, 0)),
        parseInput(
            """
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
        """));
  }

  @Test
  void parseMask() {
    assertEquals(new Mask(0b10, 0b1000000), Mask.of("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"));
  }

  @Test
  void part1Execute() {
    final List<Instruction> input =
        parseInput(
            """
                    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                    mem[8] = 11
                    mem[7] = 101
                    mem[8] = 0
                    """);

    assertEquals(
        Map.of(7L, 101L, 8L, 64L),
        execute(input, (state, instruction) -> instruction.execute(state)));
  }

  @Test
  void part2Execute() {
    final List<Instruction> input =
        parseInput(
            """
                    mask = 000000000000000000000000000000X1001X
                    mem[42] = 100
                    mask = 00000000000000000000000000000000X0XX
                    mem[26] = 1
                    """);

    assertEquals(
        Map.of(
            16L, 1L, 17L, 1L, 18L, 1L, 19L, 1L, 24L, 1L, 25L, 1L, 26L, 1L, 27L, 1L, 58L, 100L, 59L,
            100L),
        execute(input, (state, instruction) -> instruction.executeV2(state)));
  }
}
