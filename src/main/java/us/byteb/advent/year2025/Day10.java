package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Day10 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day10.txt");
    final List<Machine> machines = parse(input);
    System.out.println("Part 1: " + solvePart1(machines));
  }

  static List<Machine> parse(final String input) {
    return input.lines().map(Machine::parse).toList();
  }

  static long solvePart1(List<Machine> machines) {
    return machines.stream().mapToLong(Machine::findFewestMovesToConfigure).sum();
  }

  record Machine(boolean[] diagram, List<List<Integer>> buttonCombos) {
    static Machine parse(final String input) {
      final String diagramStr = input.substring(1, input.indexOf(']'));
      final boolean[] diagram = new boolean[diagramStr.length()];
      for (int i = 0; i < diagramStr.length(); i++) {
        diagram[i] = diagramStr.charAt(i) == '#';
      }

      final String combosStr = input.substring(input.indexOf('('), input.lastIndexOf(')') + 1);
      final List<List<Integer>> buttonCombos =
          Arrays.stream(combosStr.split(" "))
              .map(
                  str -> {
                    final String[] parts = str.substring(1, str.length() - 1).split(",");
                    return Arrays.stream(parts).map(Integer::parseInt).toList();
                  })
              .toList();

      return new Machine(diagram, buttonCombos);
    }

    int diagramBinary() {
      int value = 0;
      for (int i = diagram.length - 1; i >= 0; i--) {
        value = (value << 1) | (diagram[i] ? 1 : 0);
      }
      return value;
    }

    List<Integer> combosBinary() {
      return buttonCombos.stream()
          .map(
              combo -> {
                int value = 0;
                for (final Integer button : combo) {
                  value = value | 1 << (button);
                }
                return value;
              })
          .toList();
    }

    long findFewestMovesToConfigure() {
      final int target = diagramBinary();
      final List<Integer> combos = combosBinary();

      final Set<Integer> visited = new HashSet<>(Set.of(0));
      final Queue<Candidate> candidates = new LinkedList<>(Set.of(new Candidate(0, 0)));

      while (!candidates.isEmpty()) {
        final Candidate candidate = candidates.remove();
        for (final Integer combo : combos) {
          final int result = candidate.value() ^ combo;
          if (result == target) {
            return candidate.steps() + 1;
          }
          if (visited.add(result)) {
            candidates.add(new Candidate(result, candidate.steps() + 1));
          }
        }
      }

      throw new IllegalStateException();
    }

    record Candidate(int value, int steps) {
      @Override
      public String toString() {
        return "%s (%d)".formatted(StringUtils.leftPad(Integer.toString(value, 2), 8), steps);
      }
    }
  }
}
