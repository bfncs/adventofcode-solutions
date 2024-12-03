package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day03.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day03Test {

  private static final String EXAMPLE_DATA =
      """
      xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
      """;

  @Test
  void partOneExample() {
    final List<Mul> instructions = parseInput(EXAMPLE_DATA);
    assertEquals(
        List.of(new Mul(2, 4), new Mul(5, 5), new Mul(11, 8), new Mul(8, 5)), instructions);
    assertEquals(161, sum(instructions));
  }
}
