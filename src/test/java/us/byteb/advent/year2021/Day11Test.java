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
  void part1Example2() {
    final List<List<Octopus>> start = parseInput(example2Input);

    final StepResult expectedAfterStep1 =
        new StepResult(
            parseInput(
                """
                6594254334
                3856965822
                6375667284
                7252447257
                7468496589
                5278635756
                3287952832
                7993992245
                5957959665
                6394862637
                """),
            0);
    assertEquals(expectedAfterStep1, steps(start, 1));

    final StepResult expectedAfterStep10 =
        new StepResult(
            parseInput(
                """
                0481112976
                0031112009
                0041112504
                0081111406
                0099111306
                0093511233
                0442361130
                5532252350
                0532250600
                0032240000
                """),
            204);
    assertEquals(expectedAfterStep10, steps(start, 10));

    final StepResult expectedAfterStep100 =
        new StepResult(
            parseInput(
                """
                            0397666866
                            0749766918
                            0053976933
                            0004297822
                            0004229892
                            0053222877
                            0532222966
                            9322228966
                            7922286866
                            6789998766
                            """),
            1656);
    assertEquals(expectedAfterStep100, steps(start, 100));
  }
}
