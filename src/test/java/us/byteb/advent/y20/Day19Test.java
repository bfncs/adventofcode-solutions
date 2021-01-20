package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day19.Rule.*;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import us.byteb.advent.y20.Day19.PuzzleInput;
import us.byteb.advent.y20.Day19.RuleSet;

class Day19Test {
  private static final String EXAMPLE1_RULESET =
      """
          0: 1 2
          1: "a"
          2: 1 3 | 3 1
          3: "b"
          """;
  private static final PuzzleInput PART2_EXAMPLE =
      PuzzleInput.parse(
          """
                  42: 9 14 | 10 1
                  9: 14 27 | 1 26
                  10: 23 14 | 28 1
                  1: "a"
                  11: 42 31
                  5: 1 14 | 15 1
                  19: 14 1 | 14 14
                  12: 24 14 | 19 1
                  16: 15 1 | 14 14
                  31: 14 17 | 1 13
                  6: 14 14 | 1 14
                  2: 1 24 | 14 4
                  0: 8 11
                  13: 14 3 | 1 12
                  15: 1 | 14
                  17: 14 2 | 1 7
                  23: 25 1 | 22 14
                  28: 16 1
                  4: 1 1
                  20: 14 14 | 1 15
                  3: 5 14 | 16 1
                  27: 1 6 | 14 18
                  14: "b"
                  21: 14 1 | 1 14
                  25: 1 1 | 1 14
                  22: 14 14
                  8: 42
                  26: 14 22 | 1 20
                  18: 15 15
                  7: 14 5 | 1 21
                  24: 14 1

                  abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
                  bbabbbbaabaabba
                  babbbbaabbbbbabbbbbbaabaaabaaa
                  aaabbbbbbaaaabaababaabababbabaaabbababababaaa
                  bbbbbbbaaaabbbbaaabbabaaa
                  bbbababbbbaaaaaaaabbababaaababaabab
                  ababaaaaaabaaab
                  ababaaaaabbbaba
                  baabbaaaabbaaaababbaababb
                  abbbbabbbbaaaababbbbbbaaaababb
                  aaaaabbaabaaaaababaa
                  aaaabbaaaabbaaa
                  aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
                  babaaabbbaaabaababbaabababaaab
                  aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
                  """);

  @Test
  void parseExamples() {
    assertEquals(
        new RuleSet(
            Map.of(
                0, seq(0, ref(1), ref(2)),
                1, literal(1, 'a'),
                2, or(2, seq(2, ref(1), ref(3)), seq(2, ref(3), ref(1))),
                3, literal(3, 'b'))),
        RuleSet.parse(EXAMPLE1_RULESET));
  }

  @ParameterizedTest
  @CsvSource({
    "aab,true",
    "aba,true",
    ",false",
    "a,false",
    "aa,false",
    "aaa,false",
    "abb,false",
    "abac,false",
  })
  void validatePart1Example1(final String message, final boolean shouldBeValid) {
    final RuleSet ruleSet = RuleSet.parse(EXAMPLE1_RULESET);
    assertEquals(shouldBeValid, ruleSet.isValid(message));
  }

  @ParameterizedTest
  @CsvSource({
    "aaaabb,true",
    "aaabab,true",
    "abbabb,true",
    "abbbab,true",
    "aabaab,true",
    "aabbbb,true",
    "abaaab,true",
    "ababbb,true",
    ",false",
    "a,false",
    "baaabb,false",
    "aaaaaa,false",
    "aaaabba,false",
  })
  void validatePart1Example2(final String input, final boolean shouldBeValid) {
    final RuleSet rulesSet =
        RuleSet.parse(
            """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: "a"
            5: "b"
            """);
    assertEquals(shouldBeValid, rulesSet.isValid(input));
  }

  @Test
  void validatePart2Example() {
    assertEquals(
        Set.of("bbabbbbaabaabba", "ababaaaaaabaaab", "ababaaaaabbbaba"),
        PART2_EXAMPLE.findValidMessages());

    assertEquals(
        Set.of(
            "bbabbbbaabaabba",
            "babbbbaabbbbbabbbbbbaabaaabaaa",
            "aaabbbbbbaaaabaababaabababbabaaabbababababaaa",
            "bbbbbbbaaaabbbbaaabbabaaa",
            "bbbababbbbaaaaaaaabbababaaababaabab",
            "ababaaaaaabaaab",
            "ababaaaaabbbaba",
            "baabbaaaabbaaaababbaababb",
            "abbbbabbbbaaaababbbbbbaaaababb",
            "aaaaabbaabaaaaababaa",
            "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa",
            "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"),
        PART2_EXAMPLE.withModifiedRuleSet().findValidMessages());
  }
}
