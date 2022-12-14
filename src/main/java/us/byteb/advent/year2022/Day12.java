package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day12.txt");

    System.out.println("Part 1:" + (shortestPathFromMarkedStart(input).size() - 1));
    System.out.println("Part 2:" + (shortestPathFromAnyLowestPoint(input).size() - 1));
  }

  public static List<Position> shortestPathFromMarkedStart(final String input) {
    final HeightMap map = HeightMap.of(input);
    return shortestPath(map);
  }

  public static List<Position> shortestPathFromAnyLowestPoint(final String input) {
    final HeightMap map = HeightMap.of(input);
    return map.lowest().stream()
        .map(start -> shortestPath(map, start))
        .filter(Objects::nonNull)
        .min(Comparator.comparing(List::size))
        .orElseThrow();
  }

  private static List<Position> shortestPath(final HeightMap map, final Square start) {
    return shortestPath(map.withReplacedStart(start.position()));
  }

  private static List<Position> shortestPath(final HeightMap map) {
    Set<Square> changedSquares = new HashSet<>(Set.of(map.markedStart()));

    while (!changedSquares.isEmpty()) {
      final Set<Square> nextChangedSquares = new HashSet<>();

      for (final Square square : changedSquares) {
        for (final Square neighbour :
            map.neighbours(square.position().y(), square.position().x())) {
          if (neighbour.isReachableFrom(square)
              && (neighbour.shortestPathToStart() == null
                  || neighbour.shortestPathToStart().size()
                      > (square.shortestPathToStart().size() + 1))) {
            final Square nextSquare =
                neighbour.withShortestPathToStart(
                    concat(square.shortestPathToStart(), neighbour.position()));
            map.replace(nextSquare);
            nextChangedSquares.add(nextSquare);
          }
        }
      }

      changedSquares = nextChangedSquares;
    }

    return map.squares().stream()
        .filter(Square::isEnd)
        .findAny()
        .orElseThrow()
        .shortestPathToStart();
  }

  private static <T> List<T> concat(final List<T> xs, final T x) {
    return Stream.concat(xs.stream(), Stream.of(x)).toList();
  }

  record HeightMap(List<List<Square>> data) {
    public static HeightMap of(final String input) {
      final List<String> lines = input.lines().toList();
      final List<List<Square>> data = new ArrayList<>();
      for (int y = 0; y < lines.size(); y++) {
        final List<Square> row = new ArrayList<>();
        final char[] line = lines.get(y).toCharArray();
        for (int x = 0; x < line.length; x++) {
          final Position position = new Position(y, x);
          final char value = line[x];
          final List<Position> pathToStart = value == 'S' ? List.of(position) : null;
          row.add(new Square(position, value, pathToStart));
        }
        data.add(row);
      }

      return new HeightMap(data);
    }

    public Set<Square> squares() {
      return data.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public Square markedStart() {
      return squares().stream().filter(Square::isStart).findAny().orElseThrow();
    }

    public Set<Square> lowest() {
      return squares().stream()
          .filter(square -> square.elevation() == 'a')
          .collect(Collectors.toSet());
    }

    public Square get(final int y, final int x) {
      return data.get(y).get(x);
    }

    public Set<Square> neighbours(final int y, final int x) {
      final Set<Square> result = new HashSet<>();

      if (y > 0) {
        result.add(get(y - 1, x));
      }
      if (y < data.size() - 1) {
        result.add(get(y + 1, x));
      }
      if (x > 0) {
        result.add(get(y, x - 1));
      }
      if (x < data.get(0).size() - 1) {
        result.add(get(y, x + 1));
      }

      return result;
    }

    public void replace(final Square square) {
      final Position position = square.position();
      data.get(position.y()).set(position.x(), square);
    }

    public HeightMap withReplacedStart(final Position start) {
      return new HeightMap(
          new ArrayList<>(
              data.stream()
                  .map(
                      row ->
                          (List<Square>)
                              new ArrayList<>(
                                  row.stream()
                                      .map(
                                          square -> {
                                            if (square.position().equals(start)) {
                                              return square
                                                  .withMark('S')
                                                  .withShortestPathToStart(
                                                      List.of(square.position()));
                                            }
                                            if (square.mark() == 'S') {
                                              return square
                                                  .withMark('a')
                                                  .withShortestPathToStart(null);
                                            }
                                            return square;
                                          })
                                      .toList()))
                  .toList()));
    }
  }

  record Position(int y, int x) {}

  record Square(Position position, char mark, List<Position> shortestPathToStart) {
    public char elevation() {
      return switch (mark) {
        case 'S' -> 'a';
        case 'E' -> 'z';
        default -> mark;
      };
    }

    public boolean isStart() {
      return mark == 'S';
    }

    public boolean isEnd() {
      return mark == 'E';
    }

    public boolean isReachableFrom(final Square other) {
      return (other.elevation() + 1) >= elevation();
    }

    public Square withShortestPathToStart(final List<Position> path) {
      return new Square(position, mark, path);
    }

    public Square withMark(final char mark) {
      return new Square(position, mark, shortestPathToStart);
    }
  }
}
