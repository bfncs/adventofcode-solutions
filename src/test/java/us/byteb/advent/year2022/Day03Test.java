package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2022.Day03.*;

class Day03Test {

  private static final String PART1_EXAMPLE_DATA =
      """
      vJrwpWtwJgWrhcsFMMfFFhFp
      jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
      PmmdzqPrVvPwwTWBwg
      wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
      ttgJtRGJQctTZtZT
      CrZsJsPPZsGzwwsLwLmpwMDw
      """;

  @Test
  void partOneExample() {
    final List<Backpack> backpacks = Day03.parseInput(PART1_EXAMPLE_DATA);

    assertEquals(new Item('p'), backpacks.get(0).findDuplicateItem());
    assertEquals(new Item('L'), backpacks.get(1).findDuplicateItem());
    assertEquals(new Item('P'), backpacks.get(2).findDuplicateItem());
    assertEquals(new Item('v'), backpacks.get(3).findDuplicateItem());
    assertEquals(new Item('t'), backpacks.get(4).findDuplicateItem());
    assertEquals(new Item('s'), backpacks.get(5).findDuplicateItem());

    assertEquals(157L, Day03.sumOfPrioritiesOfDuplicateItems(backpacks));
  }

  @Test
  void itemPriority() {
    assertEquals(1, new Item('a').priority());
    assertEquals(26, new Item('z').priority());
    assertEquals(27, new Item('A').priority());
    assertEquals(52, new Item('Z').priority());
  }
}
