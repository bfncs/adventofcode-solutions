package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day13.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class Day13Test {

  private static final PuzzleInput INPUT =
      parseInput(
          """
          6,10
          0,14
          9,10
          0,3
          10,4
          4,11
          6,0
          6,12
          4,1
          0,13
          10,12
          3,4
          3,0
          8,4
          1,10
          2,14
          8,10
          9,0

          fold along y=7
          fold along x=5
          """);

  private static final Set<Dot> AFTER_STEP1 =
      parseVisualDots(
          """
                #.##..#..#.
                #...#......
                ......#...#
                #...#......
                .#.#..#.###
                ...........
                ...........
                """);

  private static final Set<Dot> AFTER_STEP2 =
      parseVisualDots(
          """
                #####
                #...#
                #...#
                #...#
                #####
                .....
                .....
                """);

  @Test
  void example1() {
    assertEquals(AFTER_STEP1, INPUT.instructions().get(0).apply(INPUT.dots()));
    assertEquals(AFTER_STEP2, INPUT.instructions().get(1).apply(AFTER_STEP1));
  }

  private static Set<Dot> parseVisualDots(final String input) {
    final Set<Dot> dots = new HashSet<>();

    final List<String> lines = input.lines().toList();
    for (int y = 0; y < lines.size(); y++) {
      final String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          dots.add(new Dot(x, y));
        }
      }
    }

    return dots;
  }
}
