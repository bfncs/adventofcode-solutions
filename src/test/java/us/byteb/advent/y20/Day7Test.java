package us.byteb.advent.y20;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

  private static final Map<String, Map<String, Integer>> RULES = Map.of(
      "light red", Map.of(
          "bright white", 1,
          "muted yellow", 2
      ),
      "dark orange", Map.of(
          "bright white", 3,
          "muted yellow", 4
      ),
      "bright white", Map.of(
          "shiny gold", 1
      ),
      "muted yellow", Map.of(
          "shiny gold", 2,
          "faded blue", 9
      ),
      "shiny gold", Map.of(
          "dark olive", 1,
          "vibrant plum", 2
      ),
      "dark olive", Map.of(
          "faded blue", 3,
          "dotted black", 4
      ),
      "vibrant plum", Map.of(
          "faded blue", 5,
          "dotted black", 6
      ),
      "faded blue", Collections.emptyMap(),
      "dotted black", Collections.emptyMap());

  @Test
  void parseExample() {
    final String input = """
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
        """;

    final Map<String, Map<String, Integer>> parsedRules = Day7.parseRules(input);
    for (final Map.Entry<String, Map<String, Integer>> entry : RULES.entrySet()) {
      assertEquals(entry.getValue(), parsedRules.get(entry.getKey()));
    }
  }

  @Test
  void solveExamplePart1() {
    assertEquals(Set.of("bright white", "muted yellow", "dark orange", "light red"),Day7.findAllPossibleContainingBagColor("shiny gold", RULES));
  }
}
