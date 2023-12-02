package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.List;

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
              final Set miniumSet = game.miniumSet();
              final boolean isValid =
                  miniumSet.red() > maxRed
                      || miniumSet.green() > maxGreen
                      || miniumSet.blue() > maxBlue;

              return isValid ? 0 : game.id();
            })
        .sum();
  }

  static long sumOfPowerOfMinimumSet(final List<Game> games) {
    return games.stream().mapToLong(game -> game.miniumSet().power()).sum();
  }

  record Game(long id, List<Set> sets) {
    public static Game parse(final String str) {
      final String[] parts = str.split(":");
      if (parts.length != 2) throw new IllegalStateException();
      final long id = Long.parseLong(parts[0].substring("Game ".length()));

      final String[] setsStr = parts[1].split(";");
      final List<Set> sets =
          Arrays.stream(setsStr)
              .map(
                  setStr -> {
                    long red = 0;
                    long green = 0;
                    long blue = 0;

                    for (final String tupleStr : setStr.split(",")) {
                      final String[] tuple = tupleStr.trim().split(" ");
                      final long count = Long.parseLong(tuple[0]);
                      if (tuple.length != 2) throw new IllegalStateException();
                      switch (tuple[1]) {
                        case "red" -> red = count;
                        case "green" -> green = count;
                        case "blue" -> blue = count;
                        default -> throw new IllegalStateException();
                      }
                    }

                    return new Set(red, green, blue);
                  })
              .toList();

      return new Game(id, sets);
    }

    public Set miniumSet() {
      long red = 0;
      long green = 0;
      long blue = 0;

      for (final Set set : sets) {
        red = Math.max(red, set.red());
        green = Math.max(green, set.green());
        blue = Math.max(blue, set.blue());
      }

      return new Set(red, green, blue);
    }
  }

  record Set(long red, long green, long blue) {
    long power() {
      return red * green * blue;
    }
  }
}
