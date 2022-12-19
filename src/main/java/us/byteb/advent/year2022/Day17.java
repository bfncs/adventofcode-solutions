package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 {

  public static final int WIDTH = 7;
  private static List<Shape> SHAPES = createShapes();

  private static List<Shape> createShapes() {
    return Arrays.stream(
            """
    ####

    .#.
    ###
    .#.

    ..#
    ..#
    ###

    #
    #
    #
    #

    ##
    ##
    """
                .split("\\R{2,}"))
        .map(Shape::parse)
        .toList();
  }

  public static void main(final String[] args) {
    final String input = readFileFromResources("year2022/day17.txt");

    System.out.println("Part 1: " + towerHeight(input, 2022));
  }

  public static long towerHeight(final String input, final int numShapes) {
    final Set<Position> stoppedRockPositions = new HashSet<>();
    int jetPatternPos = 0;

    for (int i = 0; i < numShapes; i++) {
      final Shape shape = SHAPES.get(i % SHAPES.size());

      Position position = new Position(2, positionsHeight(stoppedRockPositions) + 3);
      while (true) {
        final char jetPush = input.charAt(jetPatternPos++ % input.length());
        position = applyJetPush(stoppedRockPositions, shape, position, jetPush);

        final Position nextPosition = applyGravity(stoppedRockPositions, shape, position);
        if (nextPosition == position) {
          stoppedRockPositions.addAll(shape.at(position));
          break;
        }
        position = nextPosition;
      }
    }

    return positionsHeight(stoppedRockPositions);
  }

  private static Position applyJetPush(
      final Set<Position> stoppedRockPositions,
      final Shape shape,
      final Position position,
      final char jetPush) {
    final Position candidate =
        switch (jetPush) {
          case '<' -> {
            if (position.x() > 0) {
              yield new Position(position.x() - 1, position.y());
            } else {
              yield position;
            }
          }
          case '>' -> {
            if ((position.x() + shape.width()) < (Day17.WIDTH - 1)) {
              yield new Position(position.x() + 1, position.y());
            } else {
              yield position;
            }
          }
          default -> throw new IllegalStateException("Illegal jet push: " + jetPush);
        };

    final boolean wouldCollide =
        shape.at(candidate).stream().anyMatch(stoppedRockPositions::contains);
    return wouldCollide ? position : candidate;
  }

  private static Position applyGravity(
      final Set<Position> stoppedRockPositions, final Shape shape, final Position position) {
    final Position candidate = new Position(position.x(), position.y() - 1);
    final boolean wouldCollide =
        shape.at(candidate).stream()
            .anyMatch(pos -> pos.y() < 0 || stoppedRockPositions.contains(pos));

    return wouldCollide ? position : candidate;
  }

  private static int positionsHeight(final Set<Position> positions) {
    return positions.stream().mapToInt(position -> position.y() + 1).max().orElse(0);
  }

  record Position(int x, int y) {}

  record Shape(Set<Position> positions) {
    public static Shape parse(final String input) {
      final List<String> lines = input.lines().toList();
      final Set<Position> positions = new HashSet<>();
      for (int i = 0; i < lines.size(); i++) {
        final int y = lines.size() - i - 1;
        final String line = lines.get(i);
        for (int x = 0; x < line.toCharArray().length; x++) {
          if (line.charAt(x) == '#') {
            positions.add(new Position(x, y));
          }
        }
      }

      return new Shape(positions);
    }

    public int width() {
      return positions.stream().mapToInt(Position::x).max().orElseThrow();
    }

    public Set<Position> at(final Position lowLeftShapePosition) {
      return positions.stream()
          .map(
              pos ->
                  new Position(
                      pos.x() + lowLeftShapePosition.x(), pos.y() + lowLeftShapePosition.y()))
          .collect(Collectors.toSet());
    }
  }
}
