package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static us.byteb.advent.year2021.Day18.SnailfishNumber.leaf;
import static us.byteb.advent.year2021.Day18.SnailfishNumber.node;
import static us.byteb.advent.year2021.Day18.parseInput;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import us.byteb.advent.year2021.Day18.*;

class Day18Test {

  private static final String EXAMPLE_INPUT =
      """
      [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
      [[[5,[2,8]],4],[5,[[9,9],0]]]
      [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
      [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
      [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
      [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
      [[[[5,4],[7,7]],8],[[8,3],8]]
      [[9,3],[[9,9],[6,[4,9]]]]
      [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
      [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
      """;

  @ParameterizedTest
  @ArgumentsSource(ParseExamples.class)
  void parse(final String input, final SnailfishNumber expectedResult) {
    assertEquals(expectedResult, SnailfishNumber.parse(input));
  }

  static class ParseExamples implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
      return Stream.of(
          arguments("[1,2]", node(leaf(1), leaf(2))),
          arguments("[[1,2],3]", node(node(leaf(1), leaf(2)), leaf(3))),
          arguments("[[1,9],[8,5]]", node(node(leaf(1), leaf(9)), node(leaf(8), leaf(5)))),
          arguments(
              "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]",
              node(
                  node(
                      node(node(leaf(1), leaf(2)), node(leaf(3), leaf(4))),
                      node(node(leaf(5), leaf(6)), node(leaf(7), leaf(8)))),
                  leaf(9))),
          arguments(
              "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]",
              node(
                  node(
                      node(leaf(9), node(leaf(3), leaf(8))), node(node(leaf(0), leaf(9)), leaf(6))),
                  node(node(node(leaf(3), leaf(7)), node(leaf(4), leaf(9))), leaf(3)))),
          arguments(
              "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]",
              node(
                  node(
                      node(node(leaf(1), leaf(3)), node(leaf(5), leaf(3))),
                      node(node(leaf(1), leaf(3)), node(leaf(8), leaf(7)))),
                  node(
                      node(node(leaf(4), leaf(9)), node(leaf(6), leaf(9))),
                      node(node(leaf(8), leaf(2)), node(leaf(7), leaf(3)))))));
    }
  }

  @ParameterizedTest
  @ArgumentsSource(ReduceExamples.class)
  void reduce(final SnailfishNumber input, final SnailfishNumber expectedResult) {
    assertEquals(expectedResult, input.reduce());
  }

  static class ReduceExamples implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
      return Stream.of(
          arguments(
              SnailfishNumber.parse("[[[[[9,8],1],2],3],4]"),
              SnailfishNumber.parse("[[[[0,9],2],3],4]")),
          arguments(
              SnailfishNumber.parse("[7,[6,[5,[4,[3,2]]]]]"),
              SnailfishNumber.parse("[7,[6,[5,[7,0]]]]")),
          arguments(
              SnailfishNumber.parse("[[6,[5,[4,[3,2]]]],1]"),
              SnailfishNumber.parse("[[6,[5,[7,0]]],3]")),
          arguments(
              SnailfishNumber.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"),
              SnailfishNumber.parse("[[3,[2,[8,0]]],[9,[5,[7,0]]]]")),
          arguments(SnailfishNumber.parse("[1,10]"), SnailfishNumber.parse("[1,[5,5]]")),
          arguments(SnailfishNumber.parse("[10,1]"), SnailfishNumber.parse("[[5,5],1]")),
          arguments(SnailfishNumber.parse("[1,11]"), SnailfishNumber.parse("[1,[5,6]]")),
          arguments(SnailfishNumber.parse("[11,1]"), SnailfishNumber.parse("[[5,6],1]")),
          arguments(
              SnailfishNumber.parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"),
              SnailfishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")),
          arguments(
              SnailfishNumber.sum(
                  SnailfishNumber.parse("[[[[4,3],4],4],[7,[[8,4],9]]]"),
                  SnailfishNumber.parse("[1,1]")),
              SnailfishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")));
    }
  }

  @ParameterizedTest
  @ArgumentsSource(SumListExamples.class)
  void sumList(final String input, final String expectedResult) {
    assertEquals(SnailfishNumber.parse(expectedResult), SnailfishNumber.sum(parseInput(input)));
  }

  static class SumListExamples implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
      return Stream.of(
          arguments(
              """
              [1,1]
              [2,2]
              [3,3]
              [4,4]
              """,
              "[[[[1,1],[2,2]],[3,3]],[4,4]]"),
          arguments(
              """
              [1,1]
              [2,2]
              [3,3]
              [4,4]
              [5,5]
              """,
              "[[[[3,0],[5,3]],[4,4]],[5,5]]"),
          arguments(
              """
              [1,1]
              [2,2]
              [3,3]
              [4,4]
              [5,5]
              [6,6]
              """,
              "[[[[5,0],[7,4]],[5,5]],[6,6]]"),
          arguments(
              """
              [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
              [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
              [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
              [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
              [7,[5,[[3,8],[1,4]]]]
              [[2,[2,2]],[8,[8,1]]]
              [2,9]
              [1,[[[9,3],9],[[9,0],[0,7]]]]
              [[[5,[7,4]],7],1]
              [[[[4,2],2],6],[8,7]]
              """,
              "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"));
    }
  }

  @ParameterizedTest
  @ArgumentsSource(MagnitudeExamples.class)
  void magnitude(final String input, final long expectedResult) {
    assertEquals(expectedResult, SnailfishNumber.parse(input).magnitude());
  }

  static class MagnitudeExamples implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
      return Stream.of(
          arguments("[[1,2],[[3,4],5]]", 143),
          arguments("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384),
          arguments("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445),
          arguments("[[[[3,0],[5,3]],[4,4]],[5,5]]", 791),
          arguments("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137),
          arguments("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488));
    }
  }

  @Test
  void example1() {
    assertEquals(4140L, SnailfishNumber.sum(parseInput(EXAMPLE_INPUT)).magnitude());
  }

  @Test
  void example2() {
    assertEquals(3993L, SnailfishNumber.largestMagnitudeOfAnySumOfTwo(parseInput(EXAMPLE_INPUT)));
  }
}
