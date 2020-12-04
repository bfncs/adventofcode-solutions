package us.byteb.advent.twenty;

import static us.byteb.advent.twenty.Utils.readFileFromResources;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

  private static final Map<String, Predicate<String>> RULES =
      Map.of(
          "byr", rangeInclusive(1920, 2002),
          "iyr", rangeInclusive(2010, 2020),
          "eyr", rangeInclusive(2020, 2030),
          "hgt",
              s -> {
                if (s.endsWith("cm")) {
                  return rangeInclusive(150, 193).test(s.substring(0, s.length() - 2));
                }
                if (s.endsWith("in")) {
                  return rangeInclusive(59, 76).test(s.substring(0, s.length() - 2));
                }
                return false;
              },
          "hcl", s -> s.matches("^#[0-9a-f]{6}$"),
          "ecl", s -> List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s),
          "pid", s -> s.matches("^[0-9]{9}"));

  public static void main(String[] args) {
    final List<List<Field>> parsedInput = parseInput(readFileFromResources("day4/input.txt"));
    System.out.println("Part 1: " + filterValid(parsedInput, Day4::hasRequiredFields).size());
    System.out.println("Part 2: " + filterValid(parsedInput, Day4::hasValidFields).size());
  }

  private static Predicate<String> rangeInclusive(final int min, final int max) {
    return s -> {
      final int year = Integer.parseInt(s);
      return year >= min && year <= max;
    };
  }

  static List<List<Field>> parseInput(final String input) {
    final List<List<Field>> initialState = new ArrayList<>();
    initialState.add(new ArrayList<>());

    return input
        .lines()
        .reduce(
            initialState,
            (acc, line) -> {
              if (line.strip().length() == 0) {
                acc.add(new ArrayList<>());
              } else {
                acc.get(acc.size() - 1).addAll(parseFields(line));
              }

              return acc;
            },
            Day4::merge);
  }

  static List<List<Field>> filterValid(
      final List<List<Field>> parsedInput, final Predicate<List<Field>> policy) {
    return parsedInput.parallelStream().filter(policy).collect(Collectors.toList());
  }

  static boolean hasRequiredFields(final List<Field> item) {
    for (final String fieldKey : RULES.keySet()) {
      if (item.stream().noneMatch(candidate -> candidate.key().equals(fieldKey))) {
        return false;
      }
    }
    return true;
  }

  static boolean hasValidFields(final List<Field> item) {
    for (final Map.Entry<String, Predicate<String>> rule : RULES.entrySet()) {
      final Optional<Field> maybeField =
          item.stream().filter(candidate -> candidate.key().equals(rule.getKey())).findFirst();
      if (maybeField.isEmpty() || !rule.getValue().test(maybeField.get().value())) {
        return false;
      }
    }

    return true;
  }

  private static List<Field> parseFields(final String line) {
    return Arrays.stream(line.split("\\s+"))
        .map(
            s -> {
              final List<String> parts = Arrays.stream(s.split(":")).collect(Collectors.toList());
              if (parts.size() != 2) {
                throw new IllegalStateException("Illegal field: " + s);
              }
              return new Field(parts.get(0), parts.get(1));
            })
        .collect(Collectors.toList());
  }

  private static <T> List<T> merge(final Collection<T> left, final Collection<T> right) {
    return Stream.concat(left.stream(), right.stream()).collect(Collectors.toList());
  }

  record Field(String key, String value) {}
}
