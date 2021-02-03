package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2020.Day11.*;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class Day11Test {

  List<List<List<PositionState>>> part1ExampleSteps =
      List.of(
              """
      L.LL.LL.LL
      LLLLLLL.LL
      L.L.L..L..
      LLLL.LL.LL
      L.LL.LL.LL
      L.LLLLL.LL
      ..L.L.....
      LLLLLLLLLL
      L.LLLLLL.L
      L.LLLLL.LL
      """,
              """
      #.##.##.##
      #######.##
      #.#.#..#..
      ####.##.##
      #.##.##.##
      #.#####.##
      ..#.#.....
      ##########
      #.######.#
      #.#####.##
      """,
              """
      #.LL.L#.##
      #LLLLLL.L#
      L.L.L..L..
      #LLL.LL.L#
      #.LL.LL.LL
      #.LLLL#.##
      ..L.L.....
      #LLLLLLLL#
      #.LLLLLL.L
      #.#LLLL.##
      """,
              """
      #.##.L#.##
      #L###LL.L#
      L.#.#..#..
      #L##.##.L#
      #.##.LL.LL
      #.###L#.##
      ..#.#.....
      #L######L#
      #.LL###L.L
      #.#L###.##
      """,
              """
      #.#L.L#.##
      #LLL#LL.L#
      L.L.L..#..
      #LLL.##.L#
      #.LL.LL.LL
      #.LL#L#.##
      ..L.L.....
      #L#LLLL#L#
      #.LLLLLL.L
      #.#L#L#.##
      """,
              """
      #.#L.L#.##
      #LLL#LL.L#
      L.#.L..#..
      #L##.##.L#
      #.#L.LL.LL
      #.#L#L#.##
      ..L.L.....
      #L#L##L#L#
      #.LLLLLL.L
      #.#L#L#.##
      """)
          .stream()
          .map(Day11::parseInput)
          .collect(Collectors.toList());

  List<List<List<PositionState>>> part2ExampleSteps =
      List.of(
              """
      L.LL.LL.LL
      LLLLLLL.LL
      L.L.L..L..
      LLLL.LL.LL
      L.LL.LL.LL
      L.LLLLL.LL
      ..L.L.....
      LLLLLLLLLL
      L.LLLLLL.L
      L.LLLLL.LL
      """,
              """
      #.##.##.##
      #######.##
      #.#.#..#..
      ####.##.##
      #.##.##.##
      #.#####.##
      ..#.#.....
      ##########
      #.######.#
      #.#####.##
      """,
              """
      #.LL.LL.L#
      #LLLLLL.LL
      L.L.L..L..
      LLLL.LL.LL
      L.LL.LL.LL
      L.LLLLL.LL
      ..L.L.....
      LLLLLLLLL#
      #.LLLLLL.L
      #.LLLLL.L#
      """,
              """
      #.L#.##.L#
      #L#####.LL
      L.#.#..#..
      ##L#.##.##
      #.##.#L.##
      #.#####.#L
      ..#.#.....
      LLL####LL#
      #.L#####.L
      #.L####.L#
      """,
              """
      #.L#.L#.L#
      #LLLLLL.LL
      L.L.L..#..
      ##LL.LL.L#
      L.LL.LL.L#
      #.LLLLL.LL
      ..L.L.....
      LLLLLLLLL#
      #.LLLLL#.L
      #.L#LL#.L#
      """,
              """
      #.L#.L#.L#
      #LLLLLL.LL
      L.L.L..#..
      ##L#.#L.L#
      L.L#.#L.L#
      #.L####.LL
      ..#.#.....
      LLL###LLL#
      #.LLLLL#.L
      #.L#LL#.L#
      """,
              """
      #.L#.L#.L#
      #LLLLLL.LL
      L.L.L..#..
      ##L#.#L.L#
      L.L#.LL.L#
      #.LLLL#.LL
      ..#.L.....
      LLL###LLL#
      #.LLLLL#.L
      #.L#LL#.L#
      """)
          .stream()
          .map(Day11::parseInput)
          .collect(Collectors.toList());

  @Test
  void part1ExampleSteps() {
    assertEquals(
        part1ExampleSteps.get(1), nextState(part1ExampleSteps.get(0), Day11::part1Strategy));
    assertEquals(
        part1ExampleSteps.get(2), nextState(part1ExampleSteps.get(1), Day11::part1Strategy));
    assertEquals(
        part1ExampleSteps.get(3), nextState(part1ExampleSteps.get(2), Day11::part1Strategy));
    assertEquals(
        part1ExampleSteps.get(4), nextState(part1ExampleSteps.get(3), Day11::part1Strategy));
    assertEquals(
        part1ExampleSteps.get(5), nextState(part1ExampleSteps.get(4), Day11::part1Strategy));
    assertEquals(
        part1ExampleSteps.get(5), nextState(part1ExampleSteps.get(5), Day11::part1Strategy));
  }

  @Test
  void part1ExampleStableState() {
    final List<List<PositionState>> result =
        findStableState(part1ExampleSteps.get(0), Day11::part1Strategy);
    assertEquals(part1ExampleSteps.get(5), result);
    assertEquals(37, countOccupiedSeats(result));
  }

  @Test
  void part2ExampleSteps() {
    assertEquals(
        part2ExampleSteps.get(1), nextState(part2ExampleSteps.get(0), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(2), nextState(part2ExampleSteps.get(1), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(3), nextState(part2ExampleSteps.get(2), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(4), nextState(part2ExampleSteps.get(3), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(5), nextState(part2ExampleSteps.get(4), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(6), nextState(part2ExampleSteps.get(5), Day11::part2Strategy));
    assertEquals(
        part2ExampleSteps.get(6), nextState(part2ExampleSteps.get(6), Day11::part2Strategy));
  }
}
