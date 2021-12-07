package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day03.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day03Test {

  private static final List<Integer> example1Input =
      parseInput(
          """
           00100
           11110
           10110
           10111
           10101
           01111
           00111
           11100
           10000
           11001
           00010
           01010
           """);

  @Test
  void part1Example() {
    final long gammaRate = gammaRate(example1Input, 5);
    final long epsilonRate = epsilonRate(example1Input, 5);
    assertEquals(22, gammaRate);
    assertEquals(9, epsilonRate);
    assertEquals(198, gammaRate * epsilonRate);
  }
}
