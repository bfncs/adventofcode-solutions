package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day05.*;
import static us.byteb.advent.year2022.Day05.Crate.crate;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day05Test {

  private static final String EXAMPLE_DATA =
      """
          [D]   \s
      [N] [C]   \s
      [Z] [M] [P]
       1   2   3\s

      move 1 from 2 to 1
      move 3 from 1 to 3
      move 2 from 2 to 1
      move 1 from 1 to 2
      """;

  @Test
  void parse() {
    final PuzzleInput input = parseInput(Ega XAMPLE_DATA);

    assertEquals(
        new PuzzleInput(
            new State(
                List.of(
                    Stack.of(crate('Z'), crate('N')),
                    Stack.of(crate('M'), crate('C'), crate('D')),
                    Stack.of(crate('P')))),
            List.of(
                new Instruction(1, 2, 1),
                new Instruction(3, 1, 3),
                new Instruction(2, 2, 1),
                new Instruction(1, 1, 2))),
        input);
  }

  @Test
  void partOneExample() {
    final PuzzleInput input = parseInput(EXAMPLE_DATA);

    assertEquals(
        List.of(crate('C'), crate('M'), crate('Z')),
        input.state().apply(input.instructions(), STRATEGY1).topCrates());
  }

  @Test
  void partTwoExample() {
    final PuzzleInput input = parseInput(EXAMPLE_DATA);

    assertEquals(
        List.of(crate('M'), crate('C'), crate('D')),
        input.state().apply(input.instructions(), STRATEGY2).topCrates());
  }
}
