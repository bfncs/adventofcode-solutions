package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.byteb.advent.year2022.Day16.ValveDescription.Step;
import us.byteb.advent.year2022.Day16.ValveDescription.Step.MoveToValve;
import us.byteb.advent.year2022.Day16.ValveDescription.Step.OpenValve;

public class Day16 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2022/day16.txt");

    System.out.println("Part 1: " + maxReleasablePressure(input));
  }

  public static long maxReleasablePressure(final String input) {
    final Map<String, ValveDescription> descriptions =
        input
            .lines()
            .map(ValveDescription::parse)
            .collect(Collectors.toMap(ValveDescription::name, Function.identity()));

    final ValveDescription start = descriptions.get("AA");

    final List<Step> steps = start.findMaxPressureSteps(descriptions, List.of(), 30);
    return releasedPressure(descriptions, 30, steps);
  }

  public static int releasedPressure(
      final Map<String, ValveDescription> descriptions,
      final int maxSteps,
      final List<Step> steps) {
    int pressure = 0;
    for (int i = 0; i < steps.size(); i++) {
      final Step currentStep = steps.get(i);
      final int stepsLeft = maxSteps - i;
      if (currentStep instanceof OpenValve openValve) {
        pressure += descriptions.get(openValve.name()).rate() * stepsLeft;
      }
    }

    return pressure;
  }

  record ValveDescription(String name, int rate, Set<String> connectedValveNames) {

    private static final Pattern INPUT_PATTERN =
        Pattern.compile(
            "^Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z, ]+)$");

    public static ValveDescription parse(final String input) {
      final Matcher matcher = INPUT_PATTERN.matcher(input);
      if (!matcher.matches()) throw new IllegalStateException("Invalid input: " + input);
      return new ValveDescription(
          matcher.group(1),
          Integer.parseInt(matcher.group(2)),
          Arrays.stream(matcher.group(3).split(", ")).collect(Collectors.toSet()));
    }

    sealed interface Step {
      String name();

      record OpenValve(String name) implements Step {}

      record MoveToValve(String name) implements Step {}
    }

    public List<Step> findMaxPressureSteps(
        final Map<String, ValveDescription> descriptions,
        final List<Step> steps,
        final int maxSteps) {
      if (steps.size() >= (maxSteps)) {
        return steps;
      }

      final List<Step> stepsLeadingHere =
          Stream.concat(steps.stream(), Stream.of(new MoveToValve(name))).toList();
      final boolean shouldOpenValve =
          rate > 0 && (stepsLeadingHere.size() < maxSteps - 1) && !isAlreadyOpened(steps, name);
      final Stream<List<Step>> possibleStepsBeforeLeavingHere =
          (shouldOpenValve
                  ? Stream.of(
                      stepsLeadingHere,
                      Stream.concat(stepsLeadingHere.stream(), Stream.of(new OpenValve(name)))
                          .toList())
                  : Stream.of(stepsLeadingHere))
              .collect(Collectors.toSet()).stream();

      return possibleStepsBeforeLeavingHere
          .parallel()
          .flatMap(
              possibleSteps ->
                  connectedValveNames.stream()
                      .filter(
                          valveName ->
                              connectedValveNames.size() <= 1
                                  || steps.size() == 0
                                  || !steps.get(steps.size() - 1).name().equals(valveName))
                      .map(
                          valveName ->
                              descriptions
                                  .get(valveName)
                                  .findMaxPressureSteps(descriptions, possibleSteps, maxSteps)))
          .reduce(
              Collections.emptyList(),
              (a, b) ->
                  releasedPressure(descriptions, maxSteps, a)
                          > releasedPressure(descriptions, maxSteps, b)
                      ? a
                      : b);
    }

    private static boolean isAlreadyOpened(final List<Step> steps, final String valveName) {
      return steps.stream()
          .anyMatch(
              step -> step instanceof OpenValve openValve && openValve.name().equals(valveName));
    }
  }
}
