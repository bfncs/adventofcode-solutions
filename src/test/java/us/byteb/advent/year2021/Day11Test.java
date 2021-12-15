package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day11.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

  private static final String example1Input =
      """
      11111
      19991
      19191
      19991
      11111
      """;

  private static final String example2Input =
      """
      5483143223
      2745854711
      5264556173
      6141336146
      6357385478
      4167524645
      2176841721
      6882881134
      4846848554
      5283751526
      """;

  @Test
  void part1Example1() {
    final List<List<Octopus>> start = parseInput(example1Input);
    final StepResult step1 = step(start);

    final List<List<Octopus>> expectedGrid =
        List.of(
            List.of(o(3), o(4), o(5), o(4), o(3)),
            List.of(o(4), f(), f(), f(), o(4)),
            List.of(o(5), f(), f(), f(), o(5)),
            List.of(o(4), f(), f(), f(), o(4)),
            List.of(o(3), o(4), o(5), o(4), o(3)));

    assertEquals(new StepResult(expectedGrid, 9), step1);
    assertEquals(new StepResult(resetFlashes(expectedGrid), 9), steps(start, 1));
  }

  private static Octopus o(final int energyLevel) {
    return new Octopus(energyLevel);
  }

  private static Octopus f() {
    return new Octopus(0, true);
  }

  @Test
  void part2Example() {
    final List<List<Octopus>> start = parseInput(example2Input);
    assertEquals(195L, findFirstSynchronizedStep(start));
  }
}
