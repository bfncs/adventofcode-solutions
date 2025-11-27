package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2021.Day08.Digit.*;
import static us.byteb.advent.year2021.Day08.Segment.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day08 {

  private static final int SEGMENTS_DIGIT_ONE = 2;
  private static final int SEGMENTS_DIGIT_SEVEN = 3;
  private static final int SEGMENTS_DIGIT_FOUR = 4;
  private static final int SEGMENTS_DIGIT_EIGHT = 7;

  public static void main(String[] args) throws IOException {
    final List<PuzzleInput> input = parseInput(readFileFromResources("year2021/day08.txt"));

    System.out.println("Part 1: " + countDigitsOneFourSevenEight(input));
    System.out.println("Part 2: " + sumOfDecodedOutputValues(input));
  }

  static List<PuzzleInput> parseInput(final String input) {
    return input.lines().map(PuzzleInput::parse).toList();
  }

  static long countDigitsOneFourSevenEight(final List<PuzzleInput> inputs) {
    return inputs.stream()
        .flatMap(input -> input.outputValue().stream())
        .filter(
            value ->
                switch (value.size()) {
                  case SEGMENTS_DIGIT_ONE,
                      SEGMENTS_DIGIT_FOUR,
                      SEGMENTS_DIGIT_SEVEN,
                      SEGMENTS_DIGIT_EIGHT ->
                      true;
                  default -> false;
                })
        .count();
  }

  static long sumOfDecodedOutputValues(final List<PuzzleInput> inputs) {
    return inputs.stream().mapToLong(Day08::decodeOutputValue).sum();
  }

  static long decodeOutputValue(final PuzzleInput input) {
    final List<Set<Segment>> unknownSignalPatterns = new ArrayList<>(input.signalPatterns());
    final Map<Set<Segment>, Digit> signalPatternToDigit = new HashMap<>();
    final Map<Digit, Set<Segment>> digitToSignalPattern = new HashMap<>();
    final Map<Segment, Segment> segmentToActualSegment = new HashMap<>();

    // find all digits for signal patterns with distinct segment count
    for (final Digit digit : Digit.withDistinctSegmentCount()) {
      for (final Set<Segment> pattern : unknownSignalPatterns) {
        if (pattern.size() == digit.segments().size()) {
          signalPatternToDigit.put(pattern, digit);
          digitToSignalPattern.put(digit, pattern);
          unknownSignalPatterns.remove(pattern);
          break;
        }
      }
    }

    final Segment encodedSegmentA = findDecodedSegmentA(digitToSignalPattern);
    segmentToActualSegment.put(encodedSegmentA, A);
    final Segment encodedSegmentD =
        findEncodedSegmentD(unknownSignalPatterns, digitToSignalPattern);
    segmentToActualSegment.put(encodedSegmentD, D);

    // find digit 0
    final Set<Segment> signalPatternZero =
        Arrays.stream(Segment.values())
            .filter(segment -> !segment.equals(encodedSegmentD))
            .collect(Collectors.toSet());
    signalPatternToDigit.put(signalPatternZero, ZERO);
    digitToSignalPattern.put(ZERO, signalPatternZero);
    unknownSignalPatterns.remove(signalPatternZero);

    // find encoded segment b
    final Set<Segment> bCandidates = new HashSet<>(digitToSignalPattern.get(FOUR));
    for (final Segment segment : digitToSignalPattern.get(ONE)) {
      bCandidates.remove(segment);
    }
    bCandidates.remove(encodedSegmentD);
    if (bCandidates.size() != 1) throw new IllegalStateException();
    final Segment encodedSegmentB = bCandidates.stream().findFirst().get();
    segmentToActualSegment.put(encodedSegmentB, B);

    // find digit 5
    final Set<Set<Segment>> digit5Candidates =
        unknownSignalPatterns.stream()
            .filter(pattern -> pattern.size() == 5 && pattern.contains(encodedSegmentB))
            .collect(Collectors.toSet());
    if (digit5Candidates.size() != 1) throw new IllegalStateException();
    final Set<Segment> signalPatternFive = digit5Candidates.stream().findFirst().get();
    signalPatternToDigit.put(signalPatternFive, FIVE);
    digitToSignalPattern.put(FIVE, signalPatternFive);
    unknownSignalPatterns.remove(signalPatternFive);

    // find encoded segment c
    final Set<Segment> candidates =
        Arrays.stream(Segment.values())
            .filter(o -> !signalPatternFive.contains(o))
            .collect(Collectors.toSet());
    if (candidates.size() != 2) throw new IllegalStateException();
    final Set<Set<Segment>> twoOrThreePatternCandidates =
        unknownSignalPatterns.stream()
            .filter(pattern -> pattern.size() == 5)
            .collect(Collectors.toSet());
    Segment encodedSegmentC = null;
    Segment encodedSegmentE = null;
    for (final Segment candidate : candidates) {
      if (twoOrThreePatternCandidates.stream()
              .flatMap(Collection::stream)
              .filter(segment -> segment.equals(candidate))
              .count()
          == 2) {
        encodedSegmentC = candidate;
      } else {
        encodedSegmentE = candidate;
      }
    }
    segmentToActualSegment.put(encodedSegmentC, C);
    segmentToActualSegment.put(encodedSegmentE, E);

    // find encoded segment f & g
    Segment encodedSegmentF = null;
    Segment encodedSegmentG = null;
    for (final Segment segment : Segment.values()) {
      if (segmentToActualSegment.containsKey(segment)) {
        continue;
      }
      int notContained = 0;
      for (final Set<Segment> pattern : unknownSignalPatterns) {
        if (!pattern.contains(segment)) {
          notContained++;
        }
      }
      switch (notContained) {
        case 1 -> encodedSegmentF = segment;
        case 0 -> encodedSegmentG = segment;
      }
    }
    segmentToActualSegment.put(encodedSegmentF, F);
    segmentToActualSegment.put(encodedSegmentG, G);

    /*
    // decode other unkown digits
    for (final Set<Segment> pattern : unknownSignalPatterns) {
      final Set<Segment> decodedPattern =
          pattern.stream().map(segmentToActualSegment::get).collect(Collectors.toSet());
      final Digit digit =
          Arrays.stream(Digit.values())
              .filter(d -> d.segments().equals(decodedPattern))
              .findFirst()
              .get();
      signalPatternToDigit.put(pattern, digit);
      digitToSignalPattern.put(digit, pattern);
    }
    */

    // decode output value
    int potency = 1;
    int result = 0;
    for (int i = input.outputValue().size() - 1; i >= 0; i--) {
      final Set<Segment> pattern = input.outputValue().get(i);
      final Set<Segment> decodedPattern =
          pattern.stream().map(segmentToActualSegment::get).collect(Collectors.toSet());
      final Digit digit =
          Arrays.stream(Digit.values())
              .filter(d -> d.segments().equals(decodedPattern))
              .findFirst()
              .get();
      result += digit.value() * potency;
      potency *= 10;
    }

    return result;
  }

  private static Segment findDecodedSegmentA(final Map<Digit, Set<Segment>> digitToSignalPattern) {
    final Set<Segment> sevenSegments = new HashSet<>(digitToSignalPattern.get(SEVEN));
    for (final Segment segment : digitToSignalPattern.get(FOUR)) {
      sevenSegments.remove(segment);
    }
    if (sevenSegments.size() != 1) throw new IllegalStateException();
    final Segment a = sevenSegments.stream().findFirst().get();
    return a;
  }

  private static Segment findEncodedSegmentD(
      final List<Set<Segment>> unknownSignalPatterns,
      final Map<Digit, Set<Segment>> digitToSignalPattern) {
    final Set<Segment> dCandidates = new HashSet<>();
    for (final Segment segment : Segment.values()) {
      int missingInPatterns = 0;
      for (final Set<Segment> pattern : unknownSignalPatterns) {
        if (!pattern.contains(segment)) {
          missingInPatterns++;
        }
      }
      if (missingInPatterns == 1) {
        dCandidates.add(segment);
      }
    }
    if (dCandidates.size() != 2) throw new IllegalStateException();
    for (final Segment segment : digitToSignalPattern.get(ONE)) {
      dCandidates.remove(segment);
    }
    if (dCandidates.size() != 1) throw new IllegalStateException();
    final Segment d = dCandidates.stream().findFirst().get();
    return d;
  }

  enum Segment {
    A,
    B,
    C,
    D,
    E,
    F,
    G;

    public static Set<Segment> parseSignalPattern(final String input) {
      return input
          .chars()
          .mapToObj(
              c ->
                  switch (c) {
                    case 'a' -> A;
                    case 'b' -> B;
                    case 'c' -> C;
                    case 'd' -> D;
                    case 'e' -> E;
                    case 'f' -> F;
                    case 'g' -> G;
                    default -> throw new IllegalStateException();
                  })
          .collect(Collectors.toSet());
    }
  }

  enum Digit {
    ZERO(0, A, B, C, E, F, G),
    ONE(1, C, F),
    TWO(2, A, C, D, E, G),
    THREE(3, A, C, D, F, G),
    FOUR(4, B, C, D, F),
    FIVE(5, A, B, D, F, G),
    SIX(6, A, B, D, E, F, G),
    SEVEN(7, A, C, F),
    EIGHT(8, A, B, C, D, E, F, G),
    NINE(9, A, B, C, D, F, G);
    private int value;
    private Set<Segment> segments;

    Digit(final int value, final Segment... segments) {
      this.value = value;
      this.segments = Set.of(segments);
    }

    public int value() {
      return value;
    }

    public Set<Segment> segments() {
      return segments;
    }

    public static Set<Digit> withDistinctSegmentCount() {
      return Set.of(ONE, FOUR, SEVEN, EIGHT);
    }
  }

  record PuzzleInput(List<Set<Segment>> signalPatterns, List<Set<Segment>> outputValue) {
    public static PuzzleInput parse(final String input) {
      final String[] parts = input.split("\s+\\|\s+");
      if (parts.length != SEGMENTS_DIGIT_ONE) {
        throw new IllegalStateException("Invalid input: " + input);
      }

      final List<Set<Segment>> signalPatterns =
          Arrays.stream(parts[0].split("\s+")).map(Segment::parseSignalPattern).toList();
      final List<Set<Segment>> outputValue =
          Arrays.stream(parts[1].split("\s+")).map(Segment::parseSignalPattern).toList();

      return new PuzzleInput(signalPatterns, outputValue);
    }
  }
}
