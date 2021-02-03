package us.byteb.advent.year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import us.byteb.advent.Utils;

public class Day02 {

  public static void main(String[] args) {
    final List<Integer> input =
        Arrays.stream(Utils.readFileFromResources("year2019/day02.txt").split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toUnmodifiableList());

    System.out.println("Part 1: " + execIntCode(input, 12, 2).get(0));

    for (int noun = 0; noun <= 99; noun++) {
      for (int verb = 0; verb <= 99; verb++) {
        final List<Integer> result = execIntCode(input, noun, verb);
        if (result.get(0) == 19690720) {
          System.out.println("Part 2: " + (100 * noun + verb));
          System.exit(0);
        }
      }
    }
  }

  private static List<Integer> execIntCode(
      final List<Integer> input, final int noun, final int verb) {
    final List<Integer> memory = new ArrayList<>(input);
    memory.set(1, noun);
    memory.set(2, verb);

    return execIntCode(memory);
  }

  static List<Integer> execIntCode(final List<Integer> input) {
    return execIntCode(new ArrayList<>(input), 0);
  }

  private static List<Integer> execIntCode(List<Integer> input, int position) {
    final Integer op = input.get(position);
    if (op == 1 || op == 2) {
      final int posFirst = input.get(position + 1);
      final int posSecond = input.get(position + 2);
      final int posResult = input.get(position + 3);

      final int first = input.get(posFirst);
      final int second = input.get(posSecond);

      final int result = (op == 1) ? first + second : first * second;
      input.set(posResult, result);

      return execIntCode(input, position + 4);
    } else if (op == 99) {
      return input;
    } else {
      throw new IllegalStateException("Illegal op: " + op + " at position " + position);
    }
  }
}
