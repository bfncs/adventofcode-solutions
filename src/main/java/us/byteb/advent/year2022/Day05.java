package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day05 {

  static BiFunction<State, Instruction, State> STRATEGY1 =
      (state, instruction) -> {
        final AtomicReference<State> currentState = new AtomicReference<>(state);

        for (int repeat = 0; repeat < instruction.count(); repeat++) {
          final State nextState =
              new State(
                  IntStream.range(0, state.stacks().size())
                      .mapToObj(
                          i -> {
                            final List<Stack> stacks = currentState.get().stacks();
                            final Stack currentStack = stacks.get(i);
                            final boolean isSource = i == instruction.source() - 1;
                            final boolean isTarget = i == instruction.target() - 1;

                            if (isSource) {
                              return currentStack.withoutLast(1);
                            }

                            if (isTarget) {
                              final Stack sourceStack = stacks.get(instruction.source() - 1);
                              return currentStack.append(sourceStack.getLast(1));
                            }

                            return currentStack;
                          })
                      .toList());
          currentState.set(nextState);
        }

        return currentState.get();
      };

  static BiFunction<State, Instruction, State> STRATEGY2 =
      (state, instruction) ->
          new State(
              IntStream.range(0, state.stacks().size())
                  .mapToObj(
                      i -> {
                        final List<Stack> stacks = state.stacks();
                        final Stack currentStack = stacks.get(i);
                        final boolean isSource = i == instruction.source() - 1;
                        final boolean isTarget = i == instruction.target() - 1;

                        if (isSource) {
                          return currentStack.withoutLast(instruction.count());
                        }

                        if (isTarget) {
                          final Stack sourceStack = stacks.get(instruction.source() - 1);
                          return currentStack.append(sourceStack.getLast(instruction.count()));
                        }

                        return currentStack;
                      })
                  .toList());

  private static final Pattern INSTRUCTION_PATTERN =
      Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

  public static void main(String[] args) {
    final PuzzleInput input = parseInput(readFileFromResources("year2022/day05.txt"));

    System.out.println("Part 1: " + solve(input, STRATEGY1));
    System.out.println("Part 2: " + solve(input, STRATEGY2));
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

  private static String solve(
      final PuzzleInput input, final BiFunction<State, Instruction, State> strategy) {
    return input.state().apply(input.instructions(), strategy).topCrates().stream()
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
                        crates.add(Crate.crate(character));
                      }
                    }

                    return new Stack(crates);
                  })
              .toList();

      return new State(stacks);
    }

    State apply(
        final List<Instruction> instructions,
        final BiFunction<State, Instruction, State> strategy) {
      State currentState = this;

      for (final Instruction instruction : instructions) {
        currentState = strategy.apply(currentState, instruction);
      }

      return currentState;
    }

    List<Crate> topCrates() {
      return stacks.stream().map(stack -> stack.crates().get(stack.crates().size() - 1)).toList();
    }
  }

  record Stack(List<Crate> crates) {
    static Stack of(Crate... crates) {
      return new Stack(Arrays.stream(crates).toList());
    }

    Stack append(final Collection<Crate> addedCrates) {
      return new Stack(Stream.concat(crates.stream(), addedCrates.stream()).toList());
    }

    Stack withoutLast(final int numItems) {
      return new Stack(crates.subList(0, crates.size() - numItems));
    }

    List<Crate> getLast(final int numItems) {
      return crates.subList(crates.size() - numItems, crates.size());
    }
  }

  record Crate(Character character) {
    static Crate crate(Character character) {
      return new Crate(character);
    }
  }

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
