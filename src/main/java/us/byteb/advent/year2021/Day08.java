package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day08 {

  private static final int SEGMENTS_DIGIT_ONE = 2;
  private static final int SEGMENTS_DIGIT_SEVEN = 3;
  private static final int SEGMENTS_DIGIT_FOUR = 4;
  private static final int SEGMENTS_DIGIT_EIGHT = 7;

  public static void main(String[] args) throws IOException {
    final List<PuzzleInput> input = parseInput(readFileFromResources("year2021/day08.txt"));

    System.out.println("Part 1: " + countDigitsOneFourSevenEight(input));
  }

  static List<PuzzleInput> parseInput(final String input) {
    return input.lines().map(PuzzleInput::parse).toList();
  }

  static long countDigitsOneFourSevenEight(List<PuzzleInput> inputs) {
    return inputs.stream()
        .flatMap(input -> input.outputValue().stream())
        .filter(
            value ->
                switch (value.length()) {
                  case SEGMENTS_DIGIT_ONE,
                      SEGMENTS_DIGIT_FOUR,
                      SEGMENTS_DIGIT_SEVEN,
                      SEGMENTS_DIGIT_EIGHT -> true;
                  default -> false;
                })
        .count();
  }

  record PuzzleInput(List<String> outputValue) {
    public static PuzzleInput parse(final String input) {
      final String[] parts = input.split("\s+\\|\s+");
      if (parts.length != SEGMENTS_DIGIT_ONE) {
        throw new IllegalStateException("Invalid input: " + input);
      }

      final List<String> outputValue = Arrays.stream(parts[1].split("\s+")).toList();

      return new PuzzleInput(outputValue);
    }
  }
}
