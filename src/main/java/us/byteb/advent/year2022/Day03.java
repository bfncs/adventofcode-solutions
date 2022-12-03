package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;

public class Day03 {

  public static void main(String[] args) {
    final List<Backpack> input = parseInput(readFileFromResources("year2022/day03.txt"));

    System.out.println("Part 1: " + sumOfPrioritiesOfDuplicateItems(input));
  }

  static List<Backpack> parseInput(final String input) {
    return input.lines().map(Backpack::of).toList();
  }

  public static long sumOfPrioritiesOfDuplicateItems(final List<Backpack> backpacks) {
    return backpacks.stream().mapToLong(backpack -> backpack.findDuplicateItem().priority()).sum();
  }

  record Backpack(List<Item> compartment1, List<Item> compartment2) {
    private static Backpack of(final String input) {
      final List<Item> items = input.chars().mapToObj(c -> new Item((char) c)).toList();
      final List<Item> compartment1 = items.subList(0, items.size() / 2);
      final List<Item> compartment2 = items.subList(items.size() / 2, items.size());

      return new Backpack(compartment1, compartment2);
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
