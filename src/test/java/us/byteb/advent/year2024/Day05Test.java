package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day05.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class Day05Test {

  private static final String EXAMPLE_DATA =
      """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13

      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47
      """;

  @Test
  void partOneExample() {
    final PuzzleInput input = parseInput(EXAMPLE_DATA);
    final Set<List<Integer>> result = filterCorrectlyOrdered(input);
    assertEquals(
        Set.of(List.of(75, 47, 61, 53, 29), List.of(97, 61, 53, 29, 13), List.of(75, 29, 13)),
        result);
    assertEquals(143L, sumOfMiddleNums(result));
  }

  @Test
  void partTwoExample() {
    final PuzzleInput input = parseInput(EXAMPLE_DATA);
    final Set<List<Integer>> result = filterOnlyFixedIncorrectlyOrdered(input);
    /*
     61|13
     61|29
     29|13
    */
    assertEquals(
        Set.of(List.of(97, 75, 47, 61, 53), List.of(61, 29, 13), List.of(97, 75, 47, 29, 13)),
        result);
    assertEquals(123L, sumOfMiddleNums(result));
  }
}
