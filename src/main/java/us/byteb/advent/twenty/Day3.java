package us.byteb.advent.twenty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class Day3 {

  public static void main(String[] args) {
    final String input = readFileFromResources("day3/input.txt");

    final Field field = Field.of(input);
    Position position = new Position(0, 0);
    long counter = field.getContent(position).get() == '#' ? 1 : 0;

    while (true) {
      position = position.move(3, 1);
      final Optional<Character> content = field.getContent(position);
      if (content.isEmpty()) {
        break;
      }

      System.out.println("pos " + position + ": " + content);

      if (content.get() == '#') {
        counter += 1;
      }
    }

    System.out.println("Result: " + counter);
  }


  private static String readFileFromResources(final String fileName) {
    final ClassLoader classLoader = Day1.class.getClassLoader();
    final File file = new File(classLoader.getResource(fileName).getFile());

    try {
      return new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read file from resources: " + fileName, e);
    }
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
      return  data.get(0).length();
    }

    long getHeight() {
      return  data.size();
    }

    Optional<Character> getContent(final Position position) {
      if (position.y() >= getHeight()) {
        return Optional.empty();
      }

      return Optional.of(data.get(toIntExact(position.y())).charAt(toIntExact(position.x() % getWidth())));
    }
  }

}
