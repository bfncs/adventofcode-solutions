package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day6.parse;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day6.GroupAnswer;
import us.byteb.advent.y20.Day6.PersonAnswer;

class Day6Test {

  @Test
  void parseExample() {
    final String input =
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

    final List<GroupAnswer> groupAnswers = parse(input);
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

    assertEquals(
        11,
        groupAnswers.stream().mapToLong(groupAnswer -> groupAnswer.uniqueAnswers().size()).sum());
  }
}
