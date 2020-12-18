package us.byteb.advent.y20;

import static java.lang.Long.parseLong;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.y20.Day14.Instruction.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day14 {

  public static void main(String[] args) {
    final List<Instruction> instructions = parseInput(readFileFromResources("y20/day14.txt"));

    System.out.println(
        "Part 1: "
            + execute(instructions, (state, instruction) -> instruction.execute(state))
                .values()
                .stream()
                .mapToLong(v -> v)
                .sum());
    System.out.println(
        "Part 2: "
            + execute(instructions, (state, instruction) -> instruction.executeV2(state))
                .values()
                .stream()
                .mapToLong(v -> v)
                .sum());
  }

  static List<Instruction> parseInput(final String input) {
    return input.lines().map(Instruction::parse).collect(Collectors.toList());
  }

  static Map<Long, Long> execute(
      final List<Instruction> instructions, final BiFunction<State, Instruction, State> strategy) {
    State state = State.INITIAL;

    for (final Instruction instruction : instructions) {
      state = strategy.apply(state, instruction);
    }

    return state.memory();
  }

  interface Instruction {
    static Instruction parse(final String line) {
      final String maskPrefix = "mask = ";
      if (line.startsWith(maskPrefix)) {
        return Mask.of(line.substring(maskPrefix.length()));
      }

      if (line.startsWith("mem")) {
        final List<String> parts =
            Arrays.stream(line.split("="))
                .map(part -> part.replaceAll("\\D", ""))
                .collect(Collectors.toList());

        return new Mem(parseLong(parts.get(0)), parseLong(parts.get(1)));
      }

      throw new UnsupportedOperationException("Unable to parse line: " + line);
    }

    State execute(final State state);

    State executeV2(final State state);

    record Mask(long zeroMask, long oneMask) implements Instruction {
      public static Mask of(final String input) {
        return new Mask(
            parseLong(input.replace('1', 'X').replace('0', '1').replace('X', '0'), 2),
            parseLong(input.replace('0', 'X').replace('X', '0'), 2));
      }

      long floatingMask() {
        return ~(zeroMask | oneMask);
      }

      @Override
      public State execute(final State state) {
        return new State(state.memory(), this);
      }

      @Override
      public State executeV2(final State state) {
        return execute(state);
      }
    }

    record Mem(long targetAddress, long value) implements Instruction {
      @Override
      public State execute(final State state) {
        final Map<Long, Long> memory = new HashMap<>(state.memory());

        final long newValue = (value | state.mask().oneMask()) & ~state.mask().zeroMask();
        memory.put(targetAddress, newValue);

        return new State(memory, state.mask());
      }

      @Override
      public State executeV2(final State state) {
        final Map<Long, Long> memory = new HashMap<>(state.memory());

        for (final Long target : resolveFloatingTargetAddress(state)) {
          memory.put(target, value);
        }

        return new State(memory, state.mask());
      }

      private Set<Long> resolveFloatingTargetAddress(final State state) {
        final Set<Long> targets = new HashSet<>();
        targets.add(this.targetAddress() | state.mask().oneMask());

        for (int bit = 0; bit < 36; bit++) {
          final long currentBitMask = 1L << bit;
          if ((state.mask().floatingMask() & currentBitMask) > 0) {
            final Set<Long> nextTargets = new HashSet<>();
            for (final long target : targets) {
              nextTargets.add(target | currentBitMask);
              nextTargets.add(target ^ currentBitMask);
            }
            targets.addAll(nextTargets);
          }
        }

        return targets;
      }
    }
  }

  record State(Map<Long, Long> memory, Mask mask) {
    static final State INITIAL = new State(new HashMap<>(), new Mask(0, 0));
  }
}
