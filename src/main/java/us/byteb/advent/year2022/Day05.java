package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day05 {

  private static final Pattern INSTRUCTION_PATTERN =
      Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

  public static void main(String[] args) {
    final PuzzleInput input = parseInput(readFileFromResources("year2022/day05.txt"));

    System.out.println("Part 1: " + solvePart1(input));
  }

  static PuzzleInput parseInput(final String input) {
    final String[] parts = input.split("\\n\\s*\\n");
    if (parts.length != 2) {
      throw new IllegalStateException("Unexpected input");
    }

    final State state = State.of(parts[0]);
    final List<Instruction> instructions = parts[1].lines().map(Instruction::of).toList();

    return new PuzzleInput(state, instructions);
  }

  private static String solvePart1(final PuzzleInput input) {
    return input.state().apply(input.instructions()).topCrates().stream()
        .map(crate -> crate.character().toString())
        .collect(Collectors.joining());
  }

  record PuzzleInput(State state, List<Instruction> instructions) {}

  record State(List<Stack> stacks) {
    private static State of(final String input) {
      final List<String> lines = input.lines().toList();

      final int numStacks = lines.get(lines.size() - 1).trim().split("\\s+").length;
      final List<Stack> stacks =
          IntStream.range(0, numStacks)
              .mapToObj(
                  i -> {
                    final List<Crate> crates = new ArrayList<>();
                    for (int line = lines.size() - 2; line >= 0; line--) {
                      final char character = lines.get(line).charAt(1 + (i * 4));
                      if (character != ' ') {
                        crates.add(new Crate(character));
                      }
                    }

                    return new Stack(crates);
                  })
              .toList();

      return new State(stacks);
    }

    State apply(List<Instruction> instructions) {
      State currentState = this;

      for (final Instruction instruction : instructions) {
        currentState = currentState.apply(currentState, instruction);
      }

      return currentState;
    }

    static State apply(final State state, final Instruction instruction) {
      System.out.println("Executing " + instruction);
      final AtomicReference<State> currentState = new AtomicReference<>(state);

      for (int repeat = 0; repeat < instruction.count(); repeat++) {
        final List<Stack> stacks =
            IntStream.range(0, state.stacks().size())
                .mapToObj(
                    i -> {
                      final Stack stack = currentState.get().stacks().get(i);
                      if (i == instruction.source() - 1) {
                        return new Stack(stack.crates().subList(0, stack.crates().size() - 1));
                      } else if (i == instruction.target() - 1) {
                        final Stack sourceStack =
                            currentState.get().stacks().get(instruction.source() - 1);
                        final Crate sourceCrate =
                            sourceStack.crates().get(sourceStack.crates().size() - 1);
                        return new Stack(
                            Stream.concat(stack.crates().stream(), Stream.of(sourceCrate))
                                .toList());
                      } else {
                        return stack;
                      }
                    })
                .toList();
        currentState.set(new State(stacks));
        System.out.println(currentState.get());
      }

      return currentState.get();
    }

    List<Crate> topCrates() {
      return stacks.stream().map(stack -> stack.crates().get(stack.crates().size() - 1)).toList();
    }

    @Override
    public String toString() {
      return stacks.stream()
          .map(
              stack ->
                  stack.crates().stream()
                      .map(c -> c.character().toString())
                      .collect(Collectors.joining()))
          .collect(Collectors.joining("|"));
    }
  }

  record Stack(List<Crate> crates) {}

  record Crate(Character character) {}

  record Instruction(int count, int source, int target) {
    private static Instruction of(final String input) {
      final Matcher matcher = INSTRUCTION_PATTERN.matcher(input);
      if (!matcher.matches()) {
        throw new IllegalStateException("Invalid instruction %s".formatted(input));
      }

      final int count = Integer.parseInt(matcher.group(1));
      final int source = Integer.parseInt(matcher.group(2));
      final int target = Integer.parseInt(matcher.group(3));

      return new Instruction(count, source, target);
    }
  }
}
