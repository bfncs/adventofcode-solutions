package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 {

  private static final int NUM_COLUMNS = 3;

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day04.txt");

    System.out.println("Part 1: " + sumOfSectorIdsOfRealRooms(parseInput(input)));
  }

  static Set<Room> parseInput(final String input) {
    return input.lines().map(Room::parse).collect(Collectors.toSet());
  }

  static long sumOfSectorIdsOfRealRooms(final Set<Room> rooms) {
    return rooms.stream().filter(Room::isReal).mapToLong(Room::sectorId).sum();
  }

  record Room(String name, long sectorId, String checksum) {
    public static Room parse(final String input) {
      final String withoutChecksum = input.substring(0, input.indexOf('['));
      final String name = withoutChecksum.substring(0, withoutChecksum.lastIndexOf("-"));
      final long sectorId =
          Long.parseLong(withoutChecksum.substring(withoutChecksum.lastIndexOf("-") + 1));
      final String checksum = input.substring(input.indexOf('[') + 1, input.indexOf(']'));
      return new Room(name, sectorId, checksum);
    }

    public boolean isReal() {
      return checksum.equals(actualChecksum());
    }

    private String actualChecksum() {
      final Map<Character, Long> charCounts =
          name.chars()
              .mapToObj(c -> (char) c)
              .filter(c -> c != '-')
              .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
      return charCounts.entrySet().stream()
          .sorted(
              Map.Entry.<Character, Long>comparingByValue()
                  .reversed()
                  .thenComparing(Map.Entry.comparingByKey()))
          .map(Map.Entry::getKey)
          .limit(5)
          .map(String::valueOf)
          .collect(Collectors.joining());
    }
  }
}
