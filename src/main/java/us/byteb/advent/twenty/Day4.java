package us.byteb.advent.twenty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

  public static final List<String> REQUIRED_FIELDS =
      List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

  public static void main(String[] args) throws IOException {
    final String input = readFileFromResources("day4/input.txt");

    final List<List<String>> parsedInput = parseInput(input);
    System.out.println(countValid(parsedInput));
  }

  static long countValid(final List<List<String>> parsedInput) {
    return parsedInput.parallelStream()
        .filter(
            item -> {
              for (final String field : REQUIRED_FIELDS) {
                if (!item.stream().anyMatch(value -> value.startsWith(field))) {
                  return false;
                }
              }
              return true;
            })
        .count();
  }

  static List<List<String>> parseInput(final String input) {
    final List<List<String>> initialState = new ArrayList<>();
    initialState.add(new ArrayList<>());

    return input
        .lines()
        .reduce(
            initialState,
            (acc, line) -> {
              if (line.strip().length() == 0) {
                acc.add(new ArrayList<>());
              } else {
                acc.get(acc.size() - 1).addAll(Arrays.asList(line.split("\\s+")));
              }

              return acc;
            },
            (left, right) ->
                Stream.concat(left.stream(), right.stream()).collect(Collectors.toList()));
  }

  static String readFileFromResources(final String fileName) throws IOException {
    final ClassLoader classLoader = Day4.class.getClassLoader();
    final File file = new File(classLoader.getResource(fileName).getFile());

    return new String(Files.readAllBytes(file.toPath()));
  }
}
