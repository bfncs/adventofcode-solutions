package us.byteb.advent.twenty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 {

  public static void main(String[] args) throws Exception {
    final List<String> lines = readFileFromResources("day2/input.txt").lines().collect(Collectors.toList());

    System.out.println("part1: " + partOne(lines));
    System.out.println("part2: " + partTwo(lines));
  }

  private static Map<String, Integer> partOne(final List<String> lines) throws IOException {
    final Map<String, Integer> result = lines.parallelStream()
        .map(ValidationItem::parse)
        .map(ValidationItem::testFirstPolicy)
        .collect(Collectors.groupingBy(result1 -> result1 ? "valid" : "invalid")).entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue().size()
        ));
    return result;
  }

  private static Map<String, Integer> partTwo(final List<String> lines) throws IOException {
    return lines.parallelStream()
        .map(ValidationItem::parse)
        .map(ValidationItem::testSecondPolicy)
        .collect(Collectors.groupingBy(result1 -> result1 ? "valid" : "invalid")).entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue().size()
        ));
  }


  private static String readFileFromResources(final String fileName) throws IOException {
    final ClassLoader classLoader = Day1.class.getClassLoader();
    final File file = new File(classLoader.getResource(fileName).getFile());

    return new String(Files.readAllBytes(file.toPath()));
  }

  private record Range(int lower, int upper) {
  }

  private record ValidationRule(Range range, char requiredChar) {
  }

  record ValidationItem(ValidationRule rule, String password) {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)\\s+(\\w):\\s+(\\w+)");

    static ValidationItem parse(final String line) {
      final List<String> matches = findAllMatchingGroups(line, PATTERN);
      if (matches.size() != 4) {
        throw new IllegalStateException("Unable to parse input line: " + line);
      }
      final ValidationRule rule = new ValidationRule(new Range(Integer.parseInt(matches.get(0)), Integer.parseInt(matches.get(1))), matches.get(2).charAt(0));

      return new ValidationItem(rule, matches.get(3));
    }

    private static List<String> findAllMatchingGroups(final String str, final Pattern pattern) {
      final Matcher matcher = pattern.matcher(str);

      List<String> matches = new ArrayList<>();
      while (matcher.find()) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
          matches.add(matcher.group(i));
        }
      }

      return matches;
    }


    public final boolean testFirstPolicy() {
      final long numOccurrences = password.chars().filter(c -> c == rule.requiredChar).count();
      return numOccurrences >= rule.range().lower() && numOccurrences <= rule.range().upper();
    }


    public final boolean testSecondPolicy() {
      return password.charAt(rule.range.lower() - 1) == rule.requiredChar
          && password.charAt(rule.range.upper() - 1) != rule.requiredChar;
    }

  }


}
