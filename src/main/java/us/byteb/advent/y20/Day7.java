package us.byteb.advent.y20;

import org.apache.commons.lang3.tuple.Pair;
import us.byteb.advent.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

  public static void main(String[] args) {
    System.out.println("Part 1: " + findAllPossibleContainingBagColor("shiny gold", parseRules(Utils.readFileFromResources("y20/day7.txt"))).size());;
  }

  static Map<String, Map<String, Integer>> parseRules(final String input) {
    return input.lines()
        .map(line -> {
          final String[] parts = line.split(" bags contain ");
          return Pair.of(parts[0], parts[1]);
        })
        .collect(Collectors.toMap(
            Pair::getLeft,
        line -> {
          final String definition = line.getRight().replaceAll("\\.", "");
          if (definition.equals("no other bags")) {
            return Collections.emptyMap();
          }
          return Arrays.stream(definition
              .split(",")).map(String::strip)
              .collect(Collectors.toMap(
                  str -> str.replaceAll("\\d+", "").replaceAll("bags?", "").strip(),
                  str -> Integer.parseInt(str.replaceAll("\\D+", ""))
              ));
        }));
  }

  static Set<String> findAllPossibleContainingBagColor(final String bagColor, final Map<String, Map<String, Integer>> rules) {
    final HashSet<String> consideredColors = new HashSet<>();
    consideredColors.add(bagColor);

    final Set<String> colors = findAllPossibleContainingBagColor(bagColor, consideredColors, rules);

    colors.remove(bagColor);
    return colors;
  }

  private static Set<String> findAllPossibleContainingBagColor(final String bagColor, final Set<String> consideredColors, final Map<String, Map<String, Integer>> rules) {
    final Set<String> newPossibilities = rules.entrySet().stream()
        .filter(entry -> entry.getValue().containsKey(bagColor))
        .map(Map.Entry::getKey)
        .filter(c -> !consideredColors.contains(c))
        .collect(Collectors.toSet());

    for (final String c : newPossibilities) {
      consideredColors.add(c);

      final Map<String, Integer> colorRules = rules.get(c);
      if (!colorRules.isEmpty()) {findAllPossibleContainingBagColor(c, consideredColors, rules);
      }
    }

    return consideredColors;
  }
}
