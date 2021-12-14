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
    assertEquals(Optional.empty(), findFirstIllegalToken(tokenize("[({(<(())[]>[[{[]{<()<>>")));
    assertEquals(
        Optional.of(RIGHT_BRACE), findFirstIllegalToken(tokenize("{([(<{}[<>[]}>{[]{[(<()>")));
    assertEquals(
        Optional.of(RIGHT_PAREN), findFirstIllegalToken(tokenize("[[<[([]))<([[{}[[()]]]")));
    assertEquals(
        Optional.of(RIGHT_BRACKET), findFirstIllegalToken(tokenize("[{[{({}]{}}([{[{{{}}([]")));
    assertEquals(
        Optional.of(RIGHT_PAREN), findFirstIllegalToken(tokenize("[<(<(<(<{}))><([]([]()")));
    assertEquals(
        Optional.of(RIGHT_CHEVRON), findFirstIllegalToken(tokenize("<{([([[(<>()){}]>(<<{{")));

    assertEquals(26397L, totalSyntaxErrorScore(parseInput(exampleInput)));
  }

  @Test
  void part2Example() {
    assertEquals(
        tokenize("}}]])})]"), findMissingClosingTokens(tokenize("[({(<(())[]>[[{[]{<()<>>")));

    assertEquals(288957L, autocompleteScore(parseInput(exampleInput)));
  }
}
