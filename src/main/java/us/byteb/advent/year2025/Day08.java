package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day08 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day08.txt");
    final List<Junction> junctions = parse(input);
    System.out.println("Part 1: " + solvePart1(1_000, junctions));
    System.out.println("Part 2: " + solvePart2(junctions));
  }

  static BigInteger solvePart1(final int limit, final List<Junction> junctions) {
    final List<Pair> closestPairs = findNClosestPairs(junctions, limit);
    final Set<Set<Junction>> circuits = buildCircuits(junctions, closestPairs);

    final List<Set<Junction>> biggest3Circuits =
        circuits.stream()
            .sorted(Comparator.<Set<Junction>>comparingInt(Set::size).reversed())
            .toList()
            .subList(0, 3);

    return biggest3Circuits.stream()
        .map(Set::size)
        .map(BigInteger::valueOf)
        .reduce(BigInteger.ONE, BigInteger::multiply);
  }

  static long solvePart2(final List<Junction> junctions) {
    final Set<Junction> connected = new HashSet<>(Set.of(junctions.getFirst()));
    final Set<Junction> unconnected =
        junctions.stream()
            .filter(junction -> !connected.contains(junction))
            .collect(Collectors.toSet());

    double radius = 1.0;
    while (radius < Integer.MAX_VALUE) {
      final Queue<Junction> queue = new LinkedList<>(connected);
      while (!queue.isEmpty()) {
        final Junction candidate = queue.remove();
        for (final Junction junction : unconnected) {
          if (junction == candidate || queue.contains(junction) || connected.contains(junction)) {
            radius++;
            continue;
          }
          if (Junction.distance(candidate, junction) < radius) {
            connected.add(junction);
            queue.add(junction);
            if (connected.size() == junctions.size()) {
              return candidate.x() * junction.x();
            }
          }
        }
        unconnected.removeAll(connected);
      }

      radius++;
    }
    throw new IllegalStateException();
  }

  static long solvePart2Naive(final List<Junction> junctions) {
    for (int limit = 2; ; limit++) {
      final List<Pair> closestPairs = findNClosestPairs(junctions, limit);
      final Set<Set<Junction>> circuits = buildCircuits(junctions, closestPairs);
      System.out.println(limit + ": " + circuits.size());
      if (circuits.size() == 1) {
        return closestPairs.getLast().left().x() * closestPairs.getLast().right().x();
      }
    }
  }

  static List<Junction> parse(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.split(",");
              return new Junction(
                  Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            })
        .toList();
  }

  private static List<Pair> findNClosestPairs(final List<Junction> junctions, final int limit) {
    final SortedBuffer<Pair> buffer = new SortedBuffer<>(limit);

    for (int i = 0; i < junctions.size(); i++) {
      for (int j = i + 1; j < junctions.size(); j++) {
        final Junction a = junctions.get(i);
        final Junction b = junctions.get(j);
        final double distance = Junction.distance(a, b);
        if (!buffer.isFull() || distance < buffer.getData().getLast().distance()) {
          buffer.addItem(new Pair(a, b, distance));
        }
      }
    }

    return buffer.getData();
  }

  private static Set<Set<Junction>> buildCircuits(
      final List<Junction> junctions, final List<Pair> pairs) {
    final Set<Set<Junction>> circuits =
        junctions.stream()
            .map(junction -> new HashSet<>(Set.of(junction)))
            .collect(Collectors.toSet());

    for (final Pair pair : pairs) {
      final List<Set<Junction>> connected =
          circuits.stream()
              .filter(circuit -> circuit.contains(pair.left()) || circuit.contains(pair.right()))
              .sorted(Comparator.<Set<Junction>>comparingInt(Set::size).reversed())
              .toList();
      final Set<Junction> target = connected.getFirst();
      connected
          .subList(1, connected.size())
          .forEach(
              source -> {
                target.addAll(source);
                circuits.remove(source);
              });
    }

    return circuits;
  }

  private static class SortedBuffer<T extends Comparable<T>> {
    final List<T> data = new LinkedList<>();
    final int maxSize;

    SortedBuffer(int maxSize) {
      this.maxSize = maxSize;
    }

    void addItem(final T item) {
      if (data.isEmpty()) {
        data.add(item);
        return;
      }

      final int insertPos =
          data.stream()
              .filter(i -> i.compareTo(item) >= 0)
              .findFirst()
              .map(data::indexOf)
              .orElse(0);
      data.add(insertPos, item);

      if (data.size() > maxSize) {
        data.removeLast();
      }
    }

    List<T> getData() {
      return data;
    }

    boolean isFull() {
      return data.size() >= maxSize;
    }
  }

  record Junction(long x, long y, long z) {
    static double distance(Junction a, Junction b) {
      return Math.sqrt(
          Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2) + Math.pow(a.z() - b.z(), 2));
    }
  }

  record Pair(Junction left, Junction right, double distance) implements Comparable<Pair> {
    @Override
    public int compareTo(final Pair o) {
      return Double.compare(this.distance, o.distance);
    }
  }
}
