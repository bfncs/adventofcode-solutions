package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day19.Rule.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import us.byteb.advent.y20.Day19.Rule;

class Day19Test {

  public static final String EXAMPLE1_RULESET =
      """
          0: 1 2
          1: "a"
          2: 1 3 | 3 1
          3: "b"
          """;

  @Test
  void parseExamples() {
    final Map<Integer, Rule> expected =
        Map.of(
            0, seq(0, ref(1), ref(2)),
            1, literal(1, 'a'),
            2, or(2, seq(2, ref(1), ref(3)), seq(2, ref(3), ref(1))),
            3, literal(3, 'b'));
    assertEquals(expected, Rule.parse(EXAMPLE1_RULESET));
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
  void validatePart1Example1(final String input, final boolean shouldBeValid) {
    assertEquals(shouldBeValid, isValid(parse(EXAMPLE1_RULESET), input));
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
    final Map<Integer, Rule> rulesSet =
        parse(
            """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: "a"
            5: "b"
            """);
    assertEquals(shouldBeValid, isValid(rulesSet, input));
  }
}
