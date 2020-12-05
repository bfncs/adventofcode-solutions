package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;

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
    final List<Passport> parsedInput = parseInput(readFileFromResources("y20/day4.txt"));
    System.out.println("Part 1: " + filterValid(parsedInput, Day4::hasRequiredFields).size());
    System.out.println("Part 2: " + filterValid(parsedInput, Day4::hasValidFields).size());
  }

  private static Predicate<String> rangeInclusive(final int min, final int max) {
    return s -> {
      final int year = Integer.parseInt(s);
      return year >= min && year <= max;
    };
  }

  static List<Passport> parseInput(final String input) {
    final List<Passport> initialState = new ArrayList<>();
    initialState.add(new Passport());

    return input
        .lines()
        .reduce(
            initialState,
            (acc, line) -> {
              if (line.strip().length() == 0) {
                acc.add(new Passport());
              } else {
                final int lastItemIndex = acc.size() - 1;
                final Passport merge =
                    new Passport(merge(acc.get(lastItemIndex).fields(), parseFields(line)));
                acc.set(lastItemIndex, merge);
              }

              return acc;
            },
            Day4::merge);
  }

  static List<Passport> filterValid(
      final List<Passport> passports, final Predicate<Passport> policy) {
    return passports.parallelStream().filter(policy).collect(Collectors.toList());
  }

  static boolean hasRequiredFields(final Passport passport) {
    for (final String fieldKey : RULES.keySet()) {
      if (passport.fields().stream().noneMatch(candidate -> candidate.key().equals(fieldKey))) {
        return false;
      }
    }
    return true;
  }

  static boolean hasValidFields(final Passport passport) {
    for (final Map.Entry<String, Predicate<String>> rule : RULES.entrySet()) {
      final Optional<Field> maybeField =
          passport.fields().stream()
              .filter(candidate -> candidate.key().equals(rule.getKey()))
              .findFirst();
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

  record Passport(List<Field> fields) {
    Passport() {
      this(new ArrayList<>());
    }
  }

  record Field(String key, String value) {}
}
