package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static us.byteb.advent.year2021.Day16.*;
import static us.byteb.advent.year2021.Day16.BitsPacket.lit;
import static us.byteb.advent.year2021.Day16.BitsPacket.op;
import static us.byteb.advent.year2021.Day16.OperatorType.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class Day16Test {

  @Test
  void getBits() {
    final Blob blob = Blob.of("D2FE28");
    assertEquals(6L, blob.getBits(0, 3));
    assertEquals(4L, blob.getBits(3, 3));
    assertEquals(0b10111, blob.getBits(6, 5));
    assertEquals(0b11110, blob.getBits(11, 5));
    assertEquals(0b00101, blob.getBits(16, 5));
    assertEquals(0b01011111_11000101, blob.getBits(6, 15));
    assertEquals(0xD2FE, blob.getBits(0, 16));
    assertEquals(0x2FE2, blob.getBits(4, 16));
    assertEquals(0xFE28, blob.getBits(8, 16));
  }

  @ParameterizedTest
  @ArgumentsSource(Part1Examples.class)
  void part1Examples(
      final String input, final Result expectedResult, final Long expectedVersionSum) {
    final Result result = parse(Blob.of(input));
    assertEquals(expectedResult, result);
    assertEquals(expectedVersionSum, result.packet().versionSum());
  }

  static class Part1Examples implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
      return Stream.of(
          arguments("D2FE28", new Result(lit(6, 2021L), 21), 6L),
          arguments(
              "38006F45291200", new Result(op(1, LESS_THAN, lit(6, 10L), lit(2, 20L)), 49), 9L),
          arguments(
              "EE00D40C823060",
              new Result(op(7, MAXIMUM, lit(2, 1), lit(4, 2), lit(1, 3)), 51),
              14L),
          arguments(
              "8A004A801A8002F478",
              new Result(op(4, MINIMUM, op(1, MINIMUM, op(5, MINIMUM, lit(6, 15)))), 69),
              16L),
          arguments(
              "620080001611562C8802118E34",
              new Result(
                  op(
                      3,
                      SUM,
                      op(0, SUM, lit(0, 10), lit(5, 11)),
                      op(1, SUM, lit(0, 12), lit(3, 13))),
                  102),
              12L),
          arguments(
              "C0015000016115A2E0802F182340",
              new Result(
                  op(
                      6,
                      SUM,
                      op(0, SUM, lit(0, 10), lit(6, 11)),
                      op(4, SUM, lit(7, 12), lit(0, 13))),
                  106),
              23L),
          arguments(
              "A0016C880162017C3686B18A3D4780",
              new Result(
                  op(
                      5,
                      SUM,
                      op(
                          1,
                          SUM,
                          op(3, SUM, lit(7, 6), lit(6, 6), lit(5, 12), lit(2, 15), lit(2, 15)))),
                  113),
              31L));
    }
  }

  @Test
  void part2Examples() {
    assertEquals(3, parse(Blob.of("C200B40A82")).packet().evaluate());
    assertEquals(54, parse(Blob.of("04005AC33890")).packet().evaluate());
    assertEquals(7, parse(Blob.of("880086C3E88112")).packet().evaluate());
    assertEquals(9, parse(Blob.of("CE00C43D881120")).packet().evaluate());
    assertEquals(1, parse(Blob.of("D8005AC2A8F0")).packet().evaluate());
    assertEquals(0, parse(Blob.of("F600BC2D8F")).packet().evaluate());
    assertEquals(0, parse(Blob.of("9C005AC2F8F0")).packet().evaluate());
    assertEquals(1, parse(Blob.of("9C0141080250320F1802104A08")).packet().evaluate());
  }
}
