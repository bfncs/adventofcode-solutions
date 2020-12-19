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

    final List<Ticket> invalidTickets =
        findTicketsWithValuesNotValidForAnyField(input).stream()
            .map(ValidationResult::ticket)
            .collect(toList());
    final List<Ticket> validTickets =
        input.nearbyTickets().stream()
            .filter(ticket -> !invalidTickets.contains(ticket))
            .collect(toList());
    System.out.println(
        "Part 2: "
            + resolveOwnTicket(new Input(input.rules(), input.ownTicket(), validTickets))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith("departure"))
                .mapToLong(entry -> entry.getValue())
                .reduce((v1, v2) -> v1 * v2)
                .orElseThrow());
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
                            (acc, range) -> acc || range.contains(value),
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

  public static Map<String, Integer> resolveOwnTicket(final Input input) {
    final Map<Integer, String> fieldKey = resolveFieldKey(input);

    final Map<String, Integer> ownTicket = new HashMap<>();
    for (int i = 0; i < input.ownTicket().values().size(); i++) {
      ownTicket.put(fieldKey.get(i), input.ownTicket().values().get(i));
    }

    return ownTicket;
  }

  private static Map<String, List<Integer>> findPossibleMatches(final Input input) {
    Map<String, List<Integer>> possibleMatches = new HashMap<>();

    for (final String ruleLabel : input.rules().keySet()) {
      final List<Range> ruleRanges = input.rules().get(ruleLabel);

      possibleMatches.put(ruleLabel, new ArrayList<>());

      final int numTotalFields = input.nearbyTickets().get(0).values().size();
      for (int fieldIndex = 0; fieldIndex < numTotalFields; fieldIndex++) {
        if (checkFieldIndexValidForRule(ruleRanges, fieldIndex, input.nearbyTickets())) {
          possibleMatches.get(ruleLabel).add(fieldIndex);
        }
      }
    }

    return possibleMatches;
  }

  private static Map<Integer, String> resolveFieldKey(final Input input) {
    final Map<Integer, String> fieldKey = new HashMap<>();
    Map<String, List<Integer>> possibleMatches = new HashMap<>(findPossibleMatches(input));

    while (!possibleMatches.isEmpty()) {
      final ArrayList<Integer> matchedIndexes = new ArrayList<>();

      for (final String ruleLabel : possibleMatches.keySet()) {
        final List<Integer> matchingFieldIndexes = possibleMatches.get(ruleLabel);
        if (matchingFieldIndexes.size() == 1) {
          final Integer fieldIndex = matchingFieldIndexes.get(0);
          fieldKey.put(fieldIndex, ruleLabel);
          matchedIndexes.add(fieldIndex);
        }
      }

      final Map<String, List<Integer>> remainingPossibleMatches = new HashMap<>();
      for (final String ruleLabel : possibleMatches.keySet()) {
        final List<Integer> matchingFieldIndexes = new ArrayList<>(possibleMatches.get(ruleLabel));
        for (final int alreadyMatchedIndex : matchedIndexes) {
          final int indexOfAlreadyMatchedIndex = matchingFieldIndexes.indexOf(alreadyMatchedIndex);
          if (indexOfAlreadyMatchedIndex >= 0) {
            matchingFieldIndexes.remove(indexOfAlreadyMatchedIndex);
          }
        }
        if (matchingFieldIndexes.size() > 0) {
          remainingPossibleMatches.put(ruleLabel, matchingFieldIndexes);
        }
      }

      possibleMatches = remainingPossibleMatches;
    }

    return fieldKey;
  }

  private static boolean checkFieldIndexValidForRule(
      final List<Range> ruleRanges, final int fieldIndex, final List<Ticket> tickets) {
    for (Ticket ticket : tickets) {
      boolean fieldValid = false;
      for (Range range : ruleRanges) {
        fieldValid = fieldValid || range.contains(ticket.values().get(fieldIndex));
      }
      if (!fieldValid) {
        return false;
      }
    }

    return true;
  }

  record Input(Map<String, List<Range>> rules, Ticket ownTicket, List<Ticket> nearbyTickets) {}

  record Range(int start, int endInclusive) {
    private boolean contains(final int value) {
      return value >= this.start() && value <= this.endInclusive();
    }
  }

  record Ticket(List<Integer> values) {
    static Ticket of(int... values) {
      return new Ticket(Arrays.stream(values).boxed().collect(toList()));
    }
  }

  record ValidationResult(Ticket ticket, Optional<Integer> invalidValue) {}
}
