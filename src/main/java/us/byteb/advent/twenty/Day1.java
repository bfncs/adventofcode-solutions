package us.byteb.advent.twenty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

  public static void main(String[] args) throws IOException {
    final String lines = readFileFromResources("day1/input.csv");

    final List<Integer> sortedItems =
        lines.lines().mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());

    System.out.println(sortedItems);

    for (int i = 0; i < sortedItems.size(); i++) {
      final Integer first = sortedItems.get(i);
      for (int j = i + 1; j < sortedItems.size(); j++) {
        final Integer second = sortedItems.get(j);
        final int sum = first + second;
        if (sum == 2020) {
          System.out.printf(
              "Found it: %d + %d = %d, => %d * %d = %d%n",
              first, second, first + second, first, second, first * second);
        }
        if (sum > 2020) {
          break;
        }
      }
    }
  }

  private static String readFileFromResources(final String fileName) throws IOException {
    final ClassLoader classLoader = Day1.class.getClassLoader();
    final File file = new File(classLoader.getResource(fileName).getFile());

    return new String(Files.readAllBytes(file.toPath()));
  }
}
