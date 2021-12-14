package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day10.*;
import static us.byteb.advent.year2021.Day10.Token.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class Day10Test {

  private static final String exampleInput =
      """
          [({(<(())[]>[[{[]{<()<>>
          [(()[<>])]({[<{<<[]>>(
          {([(<{}[<>[]}>{[]{[(<()>
          (((({<>}<{<{<>}{[]{[]{}
          [[<[([]))<([[{}[[()]]]
          [{[{({}]{}}([{[{{{}}([]
          {<[[]]>}<{[{[{[]{()[[[]
          [<(<(<(<{}))><([]([]()
          <{([([[(<>()){}]>(<<{{
          <{([{{}}[<[[[<>{}]]]>[]]
          """;

  @Test
  void part1Example() {
    assertEquals(Optional.empty(), findFirstIllegalToken(tokenizeLine("[({(<(())[]>[[{[]{<()<>>")));
    assertEquals(
        Optional.of(RIGHT_BRACE), findFirstIllegalToken(tokenizeLine("{([(<{}[<>[]}>{[]{[(<()>")));
    assertEquals(
        Optional.of(RIGHT_PAREN), findFirstIllegalToken(tokenizeLine("[[<[([]))<([[{}[[()]]]")));
    assertEquals(
        Optional.of(RIGHT_BRACKET), findFirstIllegalToken(tokenizeLine("[{[{({}]{}}([{[{{{}}([]")));
    assertEquals(
        Optional.of(RIGHT_PAREN), findFirstIllegalToken(tokenizeLine("[<(<(<(<{}))><([]([]()")));
    assertEquals(
        Optional.of(RIGHT_CHEVRON), findFirstIllegalToken(tokenizeLine("<{([([[(<>()){}]>(<<{{")));

    assertEquals(26397L, totalSyntaxErrorScore(parseInput(exampleInput)));
  }
}
