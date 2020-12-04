package us.byteb.advent.twenty;

import static java.lang.Math.toIntExact;
import static us.byteb.advent.twenty.Utils.readFileFromResources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class Day3 {

  public static void main(String[] args) {
    final Field field = Field.of(readFileFromResources("day3/input.txt"));

    System.out.println("Result first part: " + calcNumTrees(field, 3, 1));

    System.out.println(
        "Result second part: "
            + List.of(Pair.of(1, 1), Pair.of(3, 1), Pair.of(5, 1), Pair.of(7, 1), Pair.of(1, 2))
                .parallelStream()
                .mapToLong(entry -> calcNumTrees(field, entry.getLeft(), entry.getRight()))
                .reduce((left, right) -> left * right)
                .getAsLong());
  }

  private static long calcNumTrees(final Field field, final int stepX, final int stepY) {
    Position position = new Position(0, 0);
    long counter = field.getContent(position).get() == '#' ? 1 : 0;

    while (true) {
      position = position.move(stepX, stepY);
      final Optional<Character> content = field.getContent(position);
      if (content.isEmpty()) {
        break;
      }

      if (content.get() == '#') {
        counter += 1;
      }
    }
    return counter;
  }

  static final record Position(long x, long y) {
    private Position move(final int moveX, final int moveY) {
      return new Position(x + moveX, y + moveY);
    }
  }

  static final class Field {
    public static Field of(final String input) {
      return new Field(input.lines().collect(Collectors.toList()));
    }

    private final List<String> data;

    private Field(final List<String> data) {
      this.data = data;
    }

    long getWidth() {
      return data.get(0).length();
    }

    long getHeight() {
      return data.size();
    }

    Optional<Character> getContent(final Position position) {
      if (position.y() >= getHeight()) {
        return Optional.empty();
      }

      return Optional.of(
          data.get(toIntExact(position.y())).charAt(toIntExact(position.x() % getWidth())));
    }
  }
}
