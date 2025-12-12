package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;

public class Day07 {

  private static final Map<CacheKey, Long> cache = new HashMap<>();

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day07.txt");
    System.out.println("Part 1: " + countSplits(input));
    System.out.println("Part 2: " + countTimelines(input));
  }

  static long countSplits(final String input) {
    final List<String> lines = input.lines().toList();

    Set<Integer> beams = new HashSet<>();
    long splits = 0L;
    for (final String line : lines) {
      final Set<Integer> nextBeams = new HashSet<>(beams);
      for (int pos = 0; pos < line.length(); pos++) {
        switch (line.charAt(pos)) {
          case 'S' -> nextBeams.add(pos);
          case '^' -> {
            if (beams.contains(pos)) {
              nextBeams.remove(pos);
              nextBeams.add(pos - 1);
              nextBeams.add(pos + 1);
              splits++;
            }
          }
        }
      }
      beams = nextBeams;
    }
    return splits;
  }

  static long countTimelines(final String input) {
    final List<String> lines = input.lines().toList();
    final int startPos = lines.getFirst().indexOf('S');

    return countTimelinesImpl(lines, 1, startPos);
  }

  private static long countTimelinesImpl(final List<String> lines, int line, int beamPos) {
    if (line == lines.size() - 1) {
      return 1L;
    }

    final CacheKey cacheKey = new CacheKey(line, beamPos);
    if (!cache.containsKey(cacheKey)) {
      cache.put(
          cacheKey,
          switch (lines.get(line).charAt(beamPos)) {
            case '^' ->
                countTimelinesImpl(lines, line + 1, beamPos - 1)
                    + countTimelinesImpl(lines, line + 1, beamPos + 1);
            case '.' -> countTimelinesImpl(lines, line + 1, beamPos);
            default ->
                throw new IllegalStateException(
                    "Unexpected value: " + lines.get(line).charAt(beamPos));
          });
    }

    return cache.get(cacheKey);
  }

  private record CacheKey(int line, int beamPos) {}
}
