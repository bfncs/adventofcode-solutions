package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2023/day05.txt");

    System.out.println("Part 1: " + solvePart1(input));
  }

  static long solvePart1(final String input) {
    final PuzzleInput puzzleInput = PuzzleInput.parse(input);

    String currentCategory = "seed";
    Set<Long> currentIds = puzzleInput.seeds();
    while (!currentCategory.equals("location")) {
      final String search = currentCategory;
      final Map currentMap =
          puzzleInput.maps().stream()
              .filter(m -> m.sourceCategory().equals(search))
              .findAny()
              .orElseThrow();
      currentCategory = currentMap.destination();
      currentIds = currentIds.stream().map(currentMap::convert).collect(Collectors.toSet());
    }

    return currentIds.stream().mapToLong(l -> l).min().orElseThrow();
  }

  record PuzzleInput(Set<Long> seeds, Set<Map> maps) {

    private static final String MAP_HEADER_SUFFIX = " map:";

    static PuzzleInput parse(final String input) {
      final List<String> lines = input.lines().toList();
      final Set<Long> seeds =
          Arrays.stream(lines.get(0).substring("seeds: ".length()).split("\\s+"))
              .map(Long::parseLong)
              .collect(Collectors.toSet());

      final Set<Map> maps = new HashSet<>();
      Map currentMap = null;
      for (int i = 1; i < lines.size(); i++) {
        final String currentLine = lines.get(i);
        if (currentLine.isEmpty() || i == lines.size() - 1) {
          if (currentMap != null) maps.add(currentMap);
        } else if (currentLine.endsWith(MAP_HEADER_SUFFIX)) {
          final String[] parts =
              currentLine
                  .substring(0, currentLine.length() - MAP_HEADER_SUFFIX.length())
                  .split("-to-");
          if (parts.length != 2) throw new IllegalStateException();
          currentMap = new Map(parts[0], parts[1]);
        } else {
          currentMap = currentMap.withRange(Range.parse(currentLine));
        }
      }

      return new PuzzleInput(seeds, maps);
    }
  }

  record Map(String sourceCategory, String destination, Set<Range> ranges) {
    public Map(final String sourceCategory, final String destination) {
      this(sourceCategory, destination, Collections.emptySet());
    }

    Map withRange(final Range range) {
      return new Map(
          sourceCategory,
          destination,
          Stream.concat(ranges.stream(), Stream.of(range)).collect(Collectors.toSet()));
    }

    public long convert(final long id) {
      final Optional<Range> matchingRange =
          ranges.stream()
              .filter(
                  range ->
                      range.sourceRangeStart() <= id
                          && (range.sourceRangeStart() + range.rangeLength) > id)
              .findAny();

      return matchingRange
          .map(range -> id - range.sourceRangeStart() + range.destinationRangeStart())
          .orElse(id);
    }
  }

  record Range(long sourceRangeStart, long destinationRangeStart, long rangeLength) {
    private static Range parse(final String currentLine) {
      final List<Long> parts =
          Arrays.stream(currentLine.split("\\s+")).map(Long::parseLong).toList();
      if (parts.size() != 3) throw new IllegalStateException();
      return new Range(parts.get(1), parts.get(0), parts.get(2));
    }
  }
}
