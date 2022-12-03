package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day03 {

  public static final int GROUP_SIZE = 3;

  public static void main(String[] args) {
    final List<Backpack> input = parseInput(readFileFromResources("year2022/day03.txt"));

    System.out.println("Part 1: " + sumOfPrioritiesOfDuplicateItems(input));
    System.out.println("Part 2: " + sumOfPrioritiesOfGroupItemTypes(input));
  }

  static List<Backpack> parseInput(final String input) {
    return input.lines().map(Backpack::of).toList();
  }

  public static long sumOfPrioritiesOfDuplicateItems(final List<Backpack> backpacks) {
    return backpacks.stream().mapToLong(backpack -> backpack.findDuplicateItem().priority()).sum();
  }

  public static long sumOfPrioritiesOfGroupItemTypes(final List<Backpack> backpacks) {
    final List<Item> groupItemTypes = new ArrayList<>();
    for (int i = 0; i < backpacks.size(); i += GROUP_SIZE) {
      final List<Backpack> group = backpacks.subList(i, i + GROUP_SIZE);
      groupItemTypes.add(findGroupItemType(group));
    }

    return groupItemTypes.stream().mapToLong(Item::priority).sum();
  }

  private static Item findGroupItemType(final List<Backpack> group) {
    for (final Item item : group.get(0).items()) {
      if (group.stream().allMatch(backpack -> backpack.contains(item))) {
        return item;
      }
    }

    throw new IllegalStateException("No group item type found");
  }

  record Backpack(List<Item> compartment1, List<Item> compartment2) {
    private static Backpack of(final String input) {
      final List<Item> items = input.chars().mapToObj(c -> new Item((char) c)).toList();
      final List<Item> compartment1 = items.subList(0, items.size() / 2);
      final List<Item> compartment2 = items.subList(items.size() / 2, items.size());

      return new Backpack(compartment1, compartment2);
    }

    public List<Item> items() {
      return Stream.concat(compartment1.stream(), compartment2.stream()).toList();
    }

    public boolean contains(final Item item) {
      return compartment1.contains(item) || compartment2.contains(item);
    }

    public Item findDuplicateItem() {
      for (final Item item : compartment1) {
        if (compartment2.contains(item)) {
          return item;
        }
      }

      throw new IllegalStateException("No duplicate item found in backpack: " + this);
    }
  }

  record Item(char character) {
    public int priority() {
      if (character >= 'a' && character <= 'z') {
        return character - 'a' + 1;
      }
      if (character >= 'A' && character <= 'Z') {
        return character - 'A' + 27;
      }

      throw new UnsupportedOperationException("Priority for character undefined: " + character);
    }
  }
}
