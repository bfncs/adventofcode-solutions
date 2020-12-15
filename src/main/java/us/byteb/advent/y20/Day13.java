package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

  public static void main(String[] args) {
    final Notes notes = parseInput(readFileFromResources("y20/day13.txt"));

    final Departure part1Departure = findEarliestPossibleDeparture(notes);
    System.out.println(
        "Part 1: "
            + part1Departure.busId() * (part1Departure.timestamp() - notes.earliestDeparture()));
  }

  static Notes parseInput(final String input) {
    final List<String> lines = input.lines().collect(Collectors.toList());
    return new Notes(
        Integer.parseInt(lines.get(0)),
        Arrays.stream(lines.get(1).split(","))
            .filter(item -> !item.equals("x"))
            .map(Integer::parseInt)
            .collect(Collectors.toList()));
  }

  static Departure findEarliestPossibleDeparture(final Notes notes) {
    return notes.busIds().stream()
        .map(busId -> new Departure(busId, ((notes.earliestDeparture() / busId) + 1) * busId))
        .min(Comparator.comparing(Departure::timestamp))
        .orElseThrow();
  }

  record Notes(int earliestDeparture, List<Integer> busIds) {}

  record Departure(int busId, int timestamp) {}
}
