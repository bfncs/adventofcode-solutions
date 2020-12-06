package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

  public static void main(String[] args) {
    final List<GroupAnswer> input = parse(readFileFromResources("y20/day6.txt"));
    System.out.println(
        "Part 1: " + input.stream().mapToLong(groupAnswer -> groupAnswer.uniqueAnswers().size()).sum());
  }

  static List<GroupAnswer> parse(final String input) {
    final ArrayList<GroupAnswer> result = new ArrayList<>();
    result.add(new GroupAnswer(new ArrayList<>()));

    return input
        .lines()
        .<List<GroupAnswer>>reduce(
            result,
            (acc, line) -> {
              if (line.isEmpty()) {
                acc.add(new GroupAnswer(new ArrayList<>()));
              } else {
                acc.get(acc.size() - 1).answers().add(PersonAnswer.of(line));
              }
              return acc;
            },
            (g1, g2) -> Stream.concat(g1.stream(), g2.stream()).collect(Collectors.toList()));
  }

  record PersonAnswer(Set<Character> answers) {
    public static PersonAnswer of(final String input) {
      return new PersonAnswer(input.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
    }
  }

  record GroupAnswer(List<PersonAnswer> answers) {
    public static GroupAnswer of(final PersonAnswer... personAnswers) {
      return new GroupAnswer(Arrays.asList(personAnswers));
    }

    public Set<Character> uniqueAnswers() {
      return answers.stream().flatMap(p -> p.answers().stream()).collect(Collectors.toSet());
    }
  }
}
