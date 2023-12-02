package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day02 {

  public static void main(String[] args) {
    final List<Game> input = parseInput(readFileFromResources("year2023/day02.txt"));

    System.out.println("Part 1: " + sumOfIdsOfPossibleGames(input, 12, 13, 14));
    System.out.println("Part 2: " + sumOfPowerOfMinimumSet(input));
  }

  static List<Game> parseInput(final String input) {
    return input.lines().map(Game::parse).toList();
  }

  static long sumOfIdsOfPossibleGames(
      final List<Game> games, final long maxRed, final long maxGreen, final long maxBlue) {
    return games.stream()
        .mapToLong(
            game -> {
              for (final Map<Color, Long> set : game.sets()) {
                if (set.getOrDefault(Color.RED, 0L) > maxRed) return 0;
                if (set.getOrDefault(Color.GREEN, 0L) > maxGreen) return 0;
                if (set.getOrDefault(Color.BLUE, 0L) > maxBlue) return 0;
              }
              return game.id();
            })
        .sum();
  }

  static long sumOfPowerOfMinimumSet(final List<Game> games) {
    return games.stream().mapToLong(Game::powerOfMinimumSet).sum();
  }

  record Game(long id, List<Map<Color, Long>> sets) {
    public static Game parse(final String str) {
      final String[] parts = str.split(":");
      if (parts.length != 2) throw new IllegalStateException();
      final long id = Long.parseLong(parts[0].substring("Game ".length()));

      final String[] setsStr = parts[1].split(";");
      final List<Map<Color, Long>> sets =
          Arrays.stream(setsStr)
              .map(
                  setStr -> {
                    final List<Map.Entry<Color, Long>> entries =
                        Arrays.stream(setStr.split(","))
                            .map(String::trim)
                            .map(
                                tupleStr -> {
                                  final String[] tuple = tupleStr.split(" ");
                                  if (tuple.length != 2) throw new IllegalStateException();
                                  return Map.entry(Color.parse(tuple[1]), Long.parseLong(tuple[0]));
                                })
                            .toList();
                    return entries.stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                  })
              .toList();

      return new Game(id, sets);
    }

    public long powerOfMinimumSet() {
      long red = 0;
      long green = 0;
      long blue = 0;

      for (final Map<Color, Long> set : sets) {
        red = Math.max(red, set.getOrDefault(Color.RED, 0L));
        green = Math.max(green, set.getOrDefault(Color.GREEN, 0L));
        blue = Math.max(blue, set.getOrDefault(Color.BLUE, 0L));
      }

      return red * green * blue;
    }
  }

  enum Color {
    RED,
    GREEN,
    BLUE;

    public static Color parse(final String str) {
      return Arrays.stream(values())
          .filter(color -> color.name().equalsIgnoreCase(str))
          .findFirst()
          .orElseThrow();
    }
  }
}
