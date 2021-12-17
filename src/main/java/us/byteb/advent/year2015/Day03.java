package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2015.Day03.Movement.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day03 {

  public static void main(String[] args) {
    final List<Movement> movements = parseInput(readFileFromResources("year2015/day03.txt"));

    System.out.println("Part 1: " + uniqueVisitedHouses(movements, 1).size());
    System.out.println("Part 2: " + uniqueVisitedHouses(movements, 2).size());
  }

  static Set<House> uniqueVisitedHouses(final List<Movement> movements, final int numActors) {
    final Set<House> visitedHouses = new HashSet<>();

    final House startingHouse = new House(0, 0);
    visitedHouses.add(startingHouse);

    int currentActor = 0;
    final List<House> actorPositions =
        new ArrayList<>(IntStream.range(0, numActors).mapToObj(a -> startingHouse).toList());

    for (final Movement movement : movements) {
      final House currentPosition = actorPositions.get(currentActor);
      final House nextPosition =
          switch (movement) {
            case NORTH -> new House(currentPosition.x() - 1, currentPosition.y());
            case SOUTH -> new House(currentPosition.x() + 1, currentPosition.y());
            case EAST -> new House(currentPosition.x(), currentPosition.y() + 1);
            case WEST -> new House(currentPosition.x(), currentPosition.y() - 1);
          };
      actorPositions.set(currentActor, nextPosition);
      visitedHouses.add(nextPosition);
      currentActor = (currentActor + 1) % actorPositions.size();
    }

    return visitedHouses;
  }

  static List<Movement> parseInput(final String input) {
    return input
        .codePoints()
        .mapToObj(
            c ->
                switch ((char) c) {
                  case '^' -> NORTH;
                  case 'v' -> SOUTH;
                  case '>' -> EAST;
                  case '<' -> WEST;
                  default -> throw new IllegalStateException();
                })
        .toList();
  }

  enum Movement {
    NORTH,
    SOUTH,
    EAST,
    WEST;
  }

  record House(long x, long y) {}
}
