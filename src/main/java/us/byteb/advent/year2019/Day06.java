package us.byteb.advent.year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static us.byteb.advent.Utils.readFileFromResources;

public class Day06 {

  public static void main(String[] args) {
    final Set<OrbitRelation> relations = parse(readFileFromResources("year2019/day06.txt"));
    System.out.println("Part 1: " + countOrbits(relations));
    System.out.println("Part 2: " + findMinNumberOfRequiredTransfers(relations));
  }

  public static Set<OrbitRelation> parse(final String input) {
    return input.lines().map(OrbitRelation::parse).collect(toSet());
  }

  public static int countOrbits(final Set<OrbitRelation> relations) {
    final Set<SpaceObject> spaceObjects =
        relations.stream()
            .flatMap(relation -> Stream.of(relation.orbitCenter(), relation.inOrbitAround()))
            .collect(toSet());

    int numOrbits = 0;
    for (final SpaceObject spaceObject : spaceObjects) {
      numOrbits += findParents(relations, spaceObject).size();
    }

    return numOrbits;
  }

  public static int findMinNumberOfRequiredTransfers(final Set<OrbitRelation> relations) {
    final List<SpaceObject> parentsYou = findParents(relations, new SpaceObject("YOU"));
    final List<SpaceObject> parentsSanta = findParents(relations, new SpaceObject("SAN"));

    for (int i = 0; i < parentsYou.size(); i++) {
      final SpaceObject youParent = parentsYou.get(i);
      for (int j = 0; j < parentsSanta.size(); j++) {
        final SpaceObject santaParent = parentsSanta.get(j);
        if (youParent.equals(santaParent)) {
          return i + j;
        }
      }
    }

    throw new IllegalStateException();
  }

  private static List<SpaceObject> findParents(
          final Set<OrbitRelation> relations, final SpaceObject spaceObject) {
    final List<SpaceObject> parents = new ArrayList<>();

    Optional<SpaceObject> maybeCurrent = Optional.of(spaceObject);
    while (maybeCurrent.isPresent()) {
      final SpaceObject current = maybeCurrent.get();
      maybeCurrent =
          relations.stream()
              .filter(r -> r.inOrbitAround().equals(current))
              .findAny()
              .map(OrbitRelation::orbitCenter);
      maybeCurrent.ifPresent(parents::add);
    }

    return parents;
  }

  public static final record SpaceObject(String name) {}

  public static final record OrbitRelation(SpaceObject orbitCenter, SpaceObject inOrbitAround) {
    public static OrbitRelation parse(final String input) {
      final String[] parts = input.split("\\)");
      return new OrbitRelation(new SpaceObject(parts[0]), new SpaceObject(parts[1]));
    }
  }
}
