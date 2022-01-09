package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class Day18 {

  public static void main(String[] args) throws IOException {
    final String input = readFileFromResources("year2021/day18.txt");
    System.out.println("Part 1: " + SnailfishNumber.sum(parseInput(input)).magnitude());
    System.out.println(
        "Part 2: " + SnailfishNumber.largestMagnitudeOfAnySumOfTwo(parseInput(input)));
  }

  static List<SnailfishNumber> parseInput(final String input) {
    return input.lines().map(SnailfishNumber::parse).toList();
  }

  interface SnailfishNumber {

    static Node node(final SnailfishNumber left, final SnailfishNumber right) {
      return new Node(left, right);
    }

    static Leaf leaf(final int value) {
      return new Leaf(value);
    }

    static SnailfishNumber parse(final String input) {
      return parseImpl(input).number();
    }

    private static ParseResult parseImpl(final String input) {
      int pos = 0;
      int expressionStart = 0;
      int stack = 0;
      SnailfishNumber left = null;

      while (pos < input.length()) {
        switch (input.charAt(pos)) {
          case '[' -> {
            if (stack == 0) {
              expressionStart = pos + 1;
            }
            stack++;
            pos++;
          }
          case ',' -> {
            if (stack <= 1) {
              final ParseResult result = parseImpl(input.substring(expressionStart, pos));
              left = result.number();
              pos++;
              expressionStart = pos;
            } else {
              pos++;
            }
          }
          case ']' -> {
            if (stack == 1) {
              if (left == null) throw new IllegalStateException();
              final ParseResult right = parseImpl(input.substring(expressionStart, pos));
              return new ParseResult(
                  new Node(left, right.number()), expressionStart + right.lastPos());
            } else {
              stack--;
              pos++;
            }
          }
          default -> {
            pos++;
          }
        }
      }

      return new ParseResult(new Leaf(Integer.parseInt(input)), input.length() - 1);
    }

    SnailfishNumber reduce();

    SnailfishNumber copy();

    static SnailfishNumber sum(final SnailfishNumber a, final SnailfishNumber b) {
      return new Node(a.copy(), b.copy()).reduce();
    }

    static SnailfishNumber sum(List<SnailfishNumber> snailfishNumbers) {
      SnailfishNumber result = snailfishNumbers.get(0);

      for (int i = 1; i < snailfishNumbers.size(); i++) {
        result = sum(result, snailfishNumbers.get(i));
      }

      return result;
    }

    static long largestMagnitudeOfAnySumOfTwo(List<SnailfishNumber> snailfishNumbers) {
      long result = Long.MIN_VALUE;

      for (final SnailfishNumber first : snailfishNumbers) {
        for (final SnailfishNumber second : snailfishNumbers) {
          if (first.equals(second)) continue;
          final long sum = SnailfishNumber.sum(first, second).magnitude();
          result = Math.max(result, sum);
        }
      }

      return result;
    }

    long magnitude();

    record ParseResult(SnailfishNumber number, int lastPos) {}

    final class Node implements SnailfishNumber {
      private SnailfishNumber left;
      private SnailfishNumber right;

      public Node(SnailfishNumber left, SnailfishNumber right) {
        this.left = left;
        this.right = right;
      }

      @Override
      public SnailfishNumber reduce() {
        boolean actionApplied;
        do {
          actionApplied = false;

          final Optional<List<SnailfishNumber>> explodeResult = findLeftmostNumberNestedInPairs(4);
          if (explodeResult.isPresent()) {
            actionApplied = true;
            explode(explodeResult.get());
            continue;
          }

          final Optional<List<SnailfishNumber>> splitResult =
              findLeftmostLeafGreaterThanOrEqual(10);
          if (splitResult.isPresent()) {
            actionApplied = true;

            split(splitResult.get());
          }

        } while (actionApplied);

        return this;
      }

      @Override
      public SnailfishNumber copy() {
        return new Node(left.copy(), right.copy());
      }

      @Override
      public long magnitude() {
        return (3 * left.magnitude()) + (2 * right.magnitude());
      }

      private void split(final List<SnailfishNumber> pathToTarget) {
        final Leaf target = (Leaf) pathToTarget.get(pathToTarget.size() - 1);
        final Node parent = (Node) pathToTarget.get(pathToTarget.size() - 2);

        final Leaf left = new Leaf(target.value() / 2);
        final Leaf right = new Leaf((int) Math.ceil((double) target.value() / 2));
        final Node replacement = new Node(left, right);

        if (parent.left().equals(target)) {
          parent.setLeft(replacement);
        } else {
          parent.setRight(replacement);
        }
      }

      private void explode(final List<SnailfishNumber> pathToTarget) {
        final Node target = (Node) pathToTarget.get(pathToTarget.size() - 1);
        final Node parent = (Node) pathToTarget.get(pathToTarget.size() - 2);

        final Optional<Leaf> leftNeighbor = findLeftNeighbor(pathToTarget);
        leftNeighbor.ifPresent(l -> l.setValue(l.value() + ((Leaf) target.left()).value()));

        final Optional<Leaf> rightNeighbor = findRightNeighbor(pathToTarget);
        rightNeighbor.ifPresent(l -> l.setValue(l.value() + ((Leaf) target.right()).value()));

        if (parent.left().equals(target)) {
          parent.setLeft(new Leaf(0));
        } else {
          parent.setRight(new Leaf(0));
        }
      }

      private Optional<Leaf> findLeftNeighbor(final List<SnailfishNumber> pathToTarget) {
        SnailfishNumber current = pathToTarget.get(pathToTarget.size() - 1);
        boolean backtracking = true;

        while (true) {
          if (backtracking) {
            final int index = pathToTarget.indexOf(current);
            if (index == 0) return Optional.empty();
            final Node parent = (Node) pathToTarget.get(index - 1);
            if (parent.right() == current) {
              current = parent.left();
              backtracking = false;
            } else {
              current = parent;
            }
          } else {
            if (current instanceof Node node) {
              current = node.right();
            } else if (current instanceof Leaf leaf) {
              return Optional.of(leaf);
            }
          }
        }
      }

      private Optional<Leaf> findRightNeighbor(final List<SnailfishNumber> pathToTarget) {
        SnailfishNumber current = pathToTarget.get(pathToTarget.size() - 1);
        boolean backtracking = true;

        while (true) {
          if (backtracking) {
            final int index = pathToTarget.indexOf(current);
            if (index == 0) return Optional.empty();
            final Node parent = (Node) pathToTarget.get(index - 1);
            if (parent.left() == current) {
              current = parent.right();
              backtracking = false;
            } else {
              current = parent;
            }
          } else {
            if (current instanceof Node node) {
              current = node.left();
            } else if (current instanceof Leaf leaf) {
              return Optional.of(leaf);
            }
          }
        }
      }

      Optional<List<SnailfishNumber>> findLeftmostLeafGreaterThanOrEqual(final int limit) {
        return findLeftmostMatch(
            (current, stack) -> current instanceof Leaf leaf && leaf.value() >= limit);
      }

      Optional<List<SnailfishNumber>> findLeftmostNumberNestedInPairs(final int numNestedPairs) {
        return findLeftmostMatch((current, stack) -> stack.size() >= numNestedPairs + 1)
            .map(path -> path.subList(0, path.size() - 1));
      }

      private Optional<List<SnailfishNumber>> findLeftmostMatch(
          final BiPredicate<SnailfishNumber, Stack<SnailfishNumber>> isMatch) {
        record NumberWithParents(SnailfishNumber number, List<SnailfishNumber> parents) {}
        final Set<NumberWithParents> visited = new HashSet<>();
        final Stack<SnailfishNumber> stack = new Stack<>();
        SnailfishNumber current = this;

        while (true) {

          if (isMatch.test(current, stack)) {
            return Optional.of(Stream.concat(stack.stream(), Stream.of(current)).toList());
          }

          if (current instanceof Node node) {
            if (!visited.contains(
                new NumberWithParents(
                    node.left(), Stream.concat(stack.stream(), Stream.of(current)).toList()))) {
              stack.push(current);
              current = node.left();
            } else if (!visited.contains(
                new NumberWithParents(
                    node.right(), Stream.concat(stack.stream(), Stream.of(current)).toList()))) {
              stack.push(current);
              current = node.right();
            } else {
              if (stack.empty()) return Optional.empty();
              visited.add(new NumberWithParents(current, stack.stream().toList()));
              current = stack.pop();
            }
          } else if (current instanceof Leaf leaf) {
            visited.add(new NumberWithParents(current, stack.stream().toList()));
            current = stack.pop();
          }
        }
      }

      public SnailfishNumber left() {
        return left;
      }

      public SnailfishNumber right() {
        return right;
      }

      public void setLeft(final SnailfishNumber left) {
        this.left = left;
      }

      public void setRight(final SnailfishNumber right) {
        this.right = right;
      }

      @Override
      public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Node) obj;
        return Objects.equals(this.left, that.left) && Objects.equals(this.right, that.right);
      }

      @Override
      public int hashCode() {
        return Objects.hash(left, right);
      }

      @Override
      public String toString() {
        return "[" + left + "," + right + ']';
      }
    }

    final class Leaf implements SnailfishNumber {
      private int value;

      public Leaf(int value) {
        this.value = value;
      }

      @Override
      public SnailfishNumber reduce() {
        throw new UnsupportedOperationException();
      }

      @Override
      public SnailfishNumber copy() {
        return new Leaf(value);
      }

      @Override
      public long magnitude() {
        return value;
      }

      public int value() {
        return value;
      }

      public void setValue(final int value) {
        this.value = value;
      }

      @Override
      public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Leaf) obj;
        return this.value == that.value;
      }

      @Override
      public int hashCode() {
        return Objects.hash(value);
      }

      @Override
      public String toString() {
        return String.valueOf(value);
      }
    }
  }
}
