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
