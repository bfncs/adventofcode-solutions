package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day6.parse;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day6.GroupAnswer;
import us.byteb.advent.y20.Day6.PersonAnswer;

class Day6Test {

  private static final String EXAMPLE_INPUT =
      """
          abc

          a
          b
          c

          ab
          ac

          a
          a
          a
          a

          b
          """;

  @Test
  void parseInput() {
    final List<GroupAnswer> groupAnswers = parse(EXAMPLE_INPUT);
    assertEquals(
        List.of(
            GroupAnswer.of(PersonAnswer.of("abc")),
            GroupAnswer.of(PersonAnswer.of("a"), PersonAnswer.of("b"), PersonAnswer.of("c")),
            GroupAnswer.of(PersonAnswer.of("ab"), PersonAnswer.of("ac")),
            GroupAnswer.of(
                PersonAnswer.of("a"),
                PersonAnswer.of("a"),
                PersonAnswer.of("a"),
                PersonAnswer.of("a")),
            GroupAnswer.of(PersonAnswer.of("b"))),
        groupAnswers);
  }

  @Test
  void examplePart1() {
    assertEquals(
        11,
        parse(EXAMPLE_INPUT).stream()
            .mapToLong(groupAnswer -> groupAnswer.uniqueAnswers().size())
            .sum());
  }

  @Test
  void examplePart2() {
    assertEquals(
        6,
        parse(EXAMPLE_INPUT).stream()
            .mapToLong(groupAnswer -> groupAnswer.sharedAnswers().size())
            .sum());
  }
}
