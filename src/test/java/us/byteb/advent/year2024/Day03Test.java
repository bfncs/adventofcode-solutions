package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day03.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day03Test {

  private static final String EXAMPLE_PART1 =
      """
      xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
      """;

  private static final String EXAMPLE_PART2 =
      """
      xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
      """;

  @Test
  void partOneExample() {
    final List<Op> instructions = parseInput(EXAMPLE_PART1);
    assertEquals(
        List.of(new Op.Mul(2, 4), new Op.Mul(5, 5), new Op.Mul(11, 8), new Op.Mul(8, 5)),
        instructions);
    assertEquals(161, evaluate(instructions, true));
  }

  @Test
  void partTwoExample() {
    final List<Op> instructions = parseInput(EXAMPLE_PART2);
    assertEquals(
        List.of(
            new Op.Mul(2, 4),
            new Op.Dont(),
            new Op.Mul(5, 5),
            new Op.Mul(11, 8),
            new Op.Do(),
            new Op.Mul(8, 5)),
        instructions);
    assertEquals(48, evaluate(instructions, false));
  }
}
