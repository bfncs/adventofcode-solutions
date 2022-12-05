package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day05.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day05Test {

  private static final String PART1_EXAMPLE_DATA =
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
  void partOneExample() {
    final PuzzleInput input = parseInput(PART1_EXAMPLE_DATA);

    assertEquals(
        new PuzzleInput(
            new State(
                List.of(
                    new Stack(List.of(new Crate('Z'), new Crate('N'))),
                    new Stack(List.of(new Crate('M'), new Crate('C'), new Crate('D'))),
                    new Stack(List.of(new Crate('P'))))),
            List.of(
                new Instruction(1, 2, 1),
                new Instruction(3, 1, 3),
                new Instruction(2, 2, 1),
                new Instruction(1, 1, 2))),
        input);

    assertEquals(
        List.of(new Crate('C'), new Crate('M'), new Crate('Z')),
        input.state().apply(input.instructions()).topCrates());
  }
}
