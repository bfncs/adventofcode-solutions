package us.byteb.advent.year2019;

import static java.util.stream.Collectors.toSet;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import us.byteb.advent.Utils;

public class Day06 {

  public static void main(String[] args) {
    final String input = Utils.readFileFromResources("year2019/day06.txt");
    System.out.println("Part 1: " + countOrbits(parse(input)));
  }

  public static Set<OrbitRelation> parse(final String input) {
    final Set<OrbitRelation> relations = input.lines().map(OrbitRelation::parse).collect(toSet());
    return relations;
  }

  public static int countOrbits(final Set<OrbitRelation> relations) {
    final Set<SpaceObject> spaceObjects =
        relations.stream()
            .flatMap(relation -> Stream.of(relation.orbitCenter(), relation.inOrbitAround()))
            .collect(toSet());

    int numOrbits = 0;
    for (final SpaceObject spaceObject : spaceObjects) {
      Optional<SpaceObject> maybeCurrent = Optional.of(spaceObject);
      while (maybeCurrent.isPresent()) {
        final SpaceObject current = maybeCurrent.get();
        maybeCurrent =
            relations.stream()
                .filter(r -> r.inOrbitAround().equals(current))
                .findAny()
                .map(OrbitRelation::orbitCenter);
        if (maybeCurrent.isPresent()) {
          numOrbits += 1;
        }
      }
    }

    return numOrbits;
  }

  static final record SpaceObject(String name) {}

  static final record OrbitRelation(SpaceObject orbitCenter, SpaceObject inOrbitAround) {
    public static OrbitRelation parse(final String input) {
      final String[] parts = input.split("\\)");
      return new OrbitRelation(new SpaceObject(parts[0]), new SpaceObject(parts[1]));
    }
  }
}
