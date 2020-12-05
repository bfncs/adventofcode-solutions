package us.byteb.advent.y19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import us.byteb.advent.Utils;

public class Day2 {

  public static void main(String[] args) {
    final List<Integer> input =
        Arrays.stream(Utils.readFileFromResources("y19/day2.txt").split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

    input.set(1, 12);
    input.set(2, 2);

    System.out.println("Part 1: " + execIntCode(input).get(0));
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
