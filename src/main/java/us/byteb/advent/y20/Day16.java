package us.byteb.advent.y20;

import static java.util.stream.Collectors.toList;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;

public class Day16 {

  public static void main(String[] args) {
    final Input input = parseInput(readFileFromResources("y20/day16.txt"));
    System.out.println(
        "Part 1: "
            + findTicketsWithValuesNotValidForAnyField(input).stream()
                .mapToLong(validationResult -> validationResult.invalidValue().orElseThrow())
                .sum());
    ;
  }

  public static Input parseInput(final String input) {
    final List<String> lines = input.lines().filter(s -> !s.isEmpty()).collect(toList());

    final Map<String, List<Range>> rules = new HashMap<>();

    int i = 0;
    while (!lines.get(i).startsWith("your ticket")) {
      final String[] parts = lines.get(i).split(":");
      final String ruleName = parts[0];

      final String[] ranges = parts[1].split("or");
      final List<Range> ruleRanges =
          Arrays.stream(ranges)
              .map(
                  str -> {
                    final List<Integer> rangeNums =
                        Arrays.stream(str.strip().split("-"))
                            .map(Integer::parseInt)
                            .collect(toList());
                    return new Range(rangeNums.get(0), rangeNums.get(1));
                  })
              .collect(toList());

      rules.put(ruleName, ruleRanges);
      i++;
    }

    final Ticket ownTicket = parseTicket(lines.get(i + 1));
    i += 3;

    final List<Ticket> nearbyTickets = new ArrayList<>();
    while (i < lines.size()) {
      nearbyTickets.add(parseTicket(lines.get(i++)));
    }

    return new Input(rules, ownTicket, nearbyTickets);
  }

  private static Ticket parseTicket(final String input) {
    return new Ticket(Arrays.stream(input.split(",")).map(Integer::parseInt).collect(toList()));
  }

  public static List<ValidationResult> findTicketsWithValuesNotValidForAnyField(final Input input) {
    return input.nearbyTickets().stream()
        .map(
            ticket -> {
              for (int value : ticket.values()) {
                final Boolean isValid =
                    input.rules().values().stream()
                        .flatMap(Collection::stream)
                        .reduce(
                            false,
                            (acc, range) ->
                                acc || (value > range.start() && value <= range.endInclusive()),
                            Boolean::logicalOr);
                if (!isValid) {
                  return new ValidationResult(ticket, Optional.of(value));
                }
              }
              return new ValidationResult(ticket, Optional.empty());
            })
        .filter(validationResult -> validationResult.invalidValue().isPresent())
        .collect(toList());
  }

  record Input(Map<String, List<Range>> rules, Ticket ownTicket, List<Ticket> nearbyTickets) {}

  record Range(int start, int endInclusive) {}

  record Ticket(List<Integer> values) {
    static Ticket of(int... values) {
      return new Ticket(Arrays.stream(values).boxed().collect(toList()));
    }
  }

  record ValidationResult(Ticket ticket, Optional<Integer> invalidValue) {}
}
