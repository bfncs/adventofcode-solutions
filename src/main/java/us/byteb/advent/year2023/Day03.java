package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day03 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2023/day03.txt");

    System.out.println("Part 1: " + sumOfPartNumbers(input));
    System.out.println("Part 2: " + sumOfGearRatios(input));
  }

  static long sumOfPartNumbers(final String input) {
    return parsePartNumbers(input).stream().mapToLong(PartNumber::number).sum();
  }

  static long sumOfGearRatios(final String input) {
    final Set<PartNumber> parts = parsePartNumbers(input);
    final Set<Symbol> symbols = parts.stream().map(PartNumber::symbol).collect(Collectors.toSet());

    return symbols.stream()
        .flatMapToLong(
            symbol -> {
              if (symbol.symbol() != '*') {
                return LongStream.empty();
              }
              final Set<PartNumber> adjacentParts =
                  parts.stream()
                      .filter(part -> part.symbol().equals(symbol))
                      .collect(Collectors.toSet());
              if (adjacentParts.size() != 2) {
                return LongStream.empty();
              }
              return LongStream.of(
                  adjacentParts.stream().mapToLong(PartNumber::number).reduce(1, (a, b) -> a * b));
            })
        .sum();
  }

  private static Set<PartNumber> parsePartNumbers(final String input) {
    final char[][] chars = input.lines().map(String::toCharArray).toArray(char[][]::new);
    final Set<PartNumber> result = new HashSet<>();
    for (int y = 0; y < chars.length; y++) {
      for (int x = 0; x < chars[y].length; x++) {
        if (!isDigit(chars[y][x])) {
          continue;
        }
        final long number = parseNumberStartingAt(chars, y, x);

        final Optional<Symbol> symbol =
            findSymbolAround(chars, y, x, x + String.valueOf(number).length());
        if (symbol.isPresent()) {
          result.add(new PartNumber(number, y, x, symbol.get()));
        }
        x += String.valueOf(number).length();
      }
    }

    return result;
  }

  private static Optional<Symbol> findSymbolAround(
      final char[][] chars, final int posY, final int posX, final int posXEnd) {
    final int minY = posY > 0 ? posY - 1 : posY;
    final int maxY = posY < chars.length - 1 ? posY + 1 : posY;
    final int minX = posX > 0 ? posX - 1 : posX;
    final int maxX = posXEnd < chars[posY].length - 1 ? posXEnd : posXEnd - 1;

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {
        if (y == posY && x >= posX && x < posXEnd) {
          continue;
        }
        final char candidate = chars[y][x];
        if (candidate != '.' && !isDigit(candidate)) {
          return Optional.of(new Symbol(candidate, y, x));
        }
      }
    }

    return Optional.empty();
  }

  private static long parseNumberStartingAt(final char[][] chars, final int y, final int x) {
    int xEnd = x;
    while (xEnd < chars[y].length && isDigit(chars[y][xEnd])) {
      xEnd++;
    }
    return Long.parseLong(new String(chars[y]).substring(x, xEnd));
  }

  private static boolean isDigit(final char character) {
    return character >= '0' && character <= '9';
  }

  record PartNumber(long number, int y, int x, Symbol symbol) {}

  record Symbol(char symbol, int y, int x) {}
}
