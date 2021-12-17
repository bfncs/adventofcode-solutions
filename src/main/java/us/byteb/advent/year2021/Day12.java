package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {

  public static void main(String[] args) throws IOException {
    final List<Edge> input = parseInput(readFileFromResources("year2021/day12.txt"));

    System.out.println("Part 1: " + allPathsVisitingSmallCavesAtMostOnce(input).size());
    System.out.println("Part 2: " + allPathsVisitingSmallCavesAtMostTwice(input).size());
  }

  static Set<List<Cave>> allPathsVisitingSmallCavesAtMostOnce(final List<Edge> edges) {
    return paths(edges, List.of(new Cave("start")), 1);
  }

  static Set<List<Cave>> allPathsVisitingSmallCavesAtMostTwice(final List<Edge> edges) {
    return paths(edges, List.of(new Cave("start")), 2);
  }

  private static Set<List<Cave>> paths(
      final List<Edge> edges, final List<Cave> history, final int maxVisitsToASingleSmallCaves) {
    final Cave currentCave = history.get(history.size() - 1);

    final boolean hasVisitedASingleSmallCaveTwice =
        history.stream()
            .filter(c -> !c.isBig())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .anyMatch(entry -> entry.getValue() > 1);

    final Set<Cave> possibleNextCaves =
        edges.stream()
            .flatMap(
                edge -> {
                  if (edge.start().equals(currentCave)) {
                    return Stream.of(edge.end());
                  } else if (edge.end().equals(currentCave)) {
                    return Stream.of(edge.start());
                  } else {
                    return Stream.empty();
                  }
                })
            .filter(
                cave -> {
                  if (cave.name().equals("start") || cave.name().equals("end")) {
                    return !history.contains(cave);
                  }

                  if (cave.isBig()) {
                    return true;
                  }

                  return !history.contains(cave)
                      || (!hasVisitedASingleSmallCaveTwice
                          && countNumberOfVisits(history, cave) < maxVisitsToASingleSmallCaves);
                })
            .collect(Collectors.toSet());

    return possibleNextCaves.stream()
        .flatMap(
            cave -> {
              final List<Cave> nextHistory =
                  Stream.concat(history.stream(), Stream.of(cave)).toList();
              if (cave.name().equals("end")) {
                return Stream.of(nextHistory);
              }
              return paths(edges, nextHistory, maxVisitsToASingleSmallCaves).stream();
            })
        .collect(Collectors.toSet());
  }

  private static long countNumberOfVisits(final List<Cave> history, final Cave cave) {
    return history.stream().filter(cave::equals).count();
  }

  static List<Edge> parseInput(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.split("-");
              if (parts.length != 2) throw new IllegalStateException();
              return new Edge(new Cave(parts[0]), new Cave(parts[1]));
            })
        .toList();
  }

  record Cave(String name) {
    boolean isBig() {
      return Character.isUpperCase(name.charAt(0));
    }
  }

  record Edge(Cave start, Cave end) {}
}
