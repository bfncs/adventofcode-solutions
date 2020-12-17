package us.byteb.advent.y20;

import static java.math.BigInteger.ZERO;
import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.byteb.advent.y20.Day13.BusIdCandidate.BusId;
import us.byteb.advent.y20.Day13.BusIdCandidate.OutOfServiceBusId;

public class Day13 {

  public static void main(String[] args) {
    final Notes notes = parseInput(readFileFromResources("y20/day13.txt"));

    final Departure part1Departure = findEarliestPossibleDeparture(notes);
    System.out.println(
        "Part 1: "
            + part1Departure.busId().id()
                * (part1Departure.timestamp() - notes.earliestDeparture()));
    System.out.println(
        "Part 2: "
            + findEarliestBusIdWithOffsetMatchingPosition(
                notes.busIdCandidates(), BigInteger.valueOf(100000000000000L)));
  }

  static Notes parseInput(final String input) {
    final List<String> lines = input.lines().collect(Collectors.toList());
    return new Notes(Integer.parseInt(lines.get(0)), parseBusIdCandidates(lines.get(1)));
  }

  static List<BusIdCandidate> parseBusIdCandidates(final String input) {
    return Arrays.stream(input.split(","))
        .map(
            item -> {
              if (item.equals("x")) {
                return new OutOfServiceBusId();
              }
              return new BusId(Integer.parseInt(item));
            })
        .collect(Collectors.toList());
  }

  static Departure findEarliestPossibleDeparture(final Notes notes) {
    return notes.busIdCandidates().stream()
        .flatMap(Day13::onlyBusIds)
        .map(
            busId ->
                new Departure(busId, ((notes.earliestDeparture() / busId.id()) + 1) * busId.id()))
        .min(Comparator.comparing(Departure::timestamp))
        .orElseThrow();
  }

  private static Stream<BusId> onlyBusIds(final BusIdCandidate candidate) {
    return (candidate instanceof BusId busId) ? Stream.of(busId) : Stream.empty();
  }

  static BigInteger findEarliestBusIdWithOffsetMatchingPosition(
      final List<BusIdCandidate> busIdCandidates) {
    return findEarliestBusIdWithOffsetMatchingPosition(busIdCandidates, ZERO);
  }

  static BigInteger findEarliestBusIdWithOffsetMatchingPosition(
      final List<BusIdCandidate> busIdCandidates, final BigInteger minTimestamp) {
    final List<BusId> busIdsSortedReverse =
        busIdCandidates.stream()
            .flatMap(Day13::onlyBusIds)
            .sorted(Comparator.comparing(BusId::id).reversed())
            .collect(Collectors.toList());

    final BusId firstBusId = (BusId) busIdCandidates.get(0);
    final BusId largestBusId = busIdsSortedReverse.get(0);

    final Map<BusId, BusIdRelation> busIdRelations = new HashMap<>();
    for (int i = 1; i < busIdsSortedReverse.size(); i++) {
      busIdRelations.put(
          busIdsSortedReverse.get(i),
          buildBusIdRelation(busIdCandidates, largestBusId, busIdsSortedReverse.get(i)));
    }

    BigInteger timestamp =
        minTimestamp.subtract(minTimestamp.mod(BigInteger.valueOf(largestBusId.id())));
    BigInteger increment = BigInteger.valueOf(largestBusId.id());

    while (true) {
      timestamp = timestamp.add(increment);

      for (int i = 1; i <= busIdsSortedReverse.size(); i++) {
        final BusId currentBusId = busIdsSortedReverse.get(i);
        final BusIdRelation relation = busIdRelations.get(currentBusId);
        final BigInteger relationOffsetCandidate =
            timestamp.add(BigInteger.valueOf(relation.offset()));
        final boolean relationOffsetCandidateValid =
            relationOffsetCandidate.mod(BigInteger.valueOf(relation.busId().id())).equals(ZERO);

        if (!relationOffsetCandidateValid) {
          break;
        }

        // We can increase increment for every new bus id for which we found a match
        // https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Search_by_sieving
        if (!(increment.mod(BigInteger.valueOf(currentBusId.id())).equals(ZERO))) {
          increment = increment.multiply(BigInteger.valueOf(currentBusId.id()));
        }

        // If we found match for the smallest busId, we're done
        if (i == busIdsSortedReverse.size() - 1) {
          final BusIdRelation relationToFirstCandidate =
              buildBusIdRelation(busIdCandidates, largestBusId, firstBusId);
          return timestamp.add(BigInteger.valueOf(relationToFirstCandidate.offset()));
        }
      }
    }
  }

  private static BusIdRelation buildBusIdRelation(
      final List<BusIdCandidate> busIdCandidates, final BusId currentId, final BusId relatedId) {
    final int currentIdPosition = busIdCandidates.indexOf(currentId);
    final int relatedIdPosition = busIdCandidates.indexOf(relatedId);
    final int offset = relatedIdPosition - currentIdPosition;
    return new BusIdRelation(relatedId, offset);
  }

  record Notes(int earliestDeparture, List<BusIdCandidate> busIdCandidates) {}

  interface BusIdCandidate {
    record OutOfServiceBusId() implements BusIdCandidate {}

    record BusId(int id) implements BusIdCandidate {}
  }

  record Departure(BusId busId, int timestamp) {}

  record BusIdRelation(BusId busId, int offset) {}
}
