package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import us.byteb.advent.year2021.Day16.BitsPacket.LiteralValue;
import us.byteb.advent.year2021.Day16.BitsPacket.Operator;

public class Day16 {

  private static final int NUM_BITS_LITERAL_GROUP = 5;

  public static void main(String[] args) throws IOException {
    final Blob input = Blob.of(readFileFromResources("year2021/day16.txt"));
    final BitsPacket packet = parse(input).packet();
    System.out.println("Part 1: " + packet.versionSum());
    System.out.println("Part 2: " + packet.evaluate());
  }

  record Result(BitsPacket packet, int nextUnusedBitOffset) {}

  static Result parse(final Blob blob) {
    return parse(blob, 0);
  }

  static Result parse(final Blob blob, final int offset) {
    final int packetVersion = (int) blob.getBits(offset, 3);
    final int packetTypeId = (int) blob.getBits(offset + 3, 3);

    if (packetTypeId == 4) {
      int pointer = offset + 6 - NUM_BITS_LITERAL_GROUP;
      long value = 0;
      do {
        pointer += NUM_BITS_LITERAL_GROUP;
        final long nextFourBits = blob.getBits(pointer + 1, 4);
        value = (value << 4) | nextFourBits;
      } while (blob.getBits(pointer, 1) > 0);

      return new Result(new LiteralValue(packetVersion, value), pointer + NUM_BITS_LITERAL_GROUP);
    }

    final OperatorType operatorType = OperatorType.of((int) blob.getBits(offset + 3, 3));
    final long lengthTypeId = blob.getBits(offset + 6, 1);

    if (lengthTypeId == 0) {
      final long length = blob.getBits(offset + 7, 15);
      final int payloadStart = offset + 7 + 15;

      int pointer = payloadStart;
      final List<BitsPacket> subPackets = new ArrayList<>();
      while (pointer < payloadStart + length) {
        final Result nextResult = parse(blob, pointer);
        subPackets.add(nextResult.packet());
        pointer = nextResult.nextUnusedBitOffset();
      }

      return new Result(new Operator(packetVersion, operatorType, subPackets), pointer);
    } else if (lengthTypeId == 1) {
      final long numberOfSubPackets = blob.getBits(offset + 7, 11);

      int pointer = offset + 7 + 11;
      final List<BitsPacket> subPackets = new ArrayList<>();
      while (subPackets.size() < numberOfSubPackets) {
        final Result nextResult = parse(blob, pointer);
        subPackets.add(nextResult.packet());
        pointer = nextResult.nextUnusedBitOffset();
      }

      return new Result(new Operator(packetVersion, operatorType, subPackets), pointer);
    }

    throw new UnsupportedOperationException();
  }

  record Blob(byte[] data, int offset) {
    public static Blob of(final String hexadecimalInput) {
      return new Blob(HexFormat.of().parseHex(hexadecimalInput), 0);
    }

    public long getBits(final int fromBit, final int length) {
      if (length >= 64) {
        throw new UnsupportedOperationException();
      }
      final int start = fromBit + offset;

      final int firstByteIndex = start / 8;
      final int lastByteIndex = (start + length) / 8;
      final int numInvolvedBytes = lastByteIndex - firstByteIndex;

      long result = 0;

      for (int i = 0; i <= numInvolvedBytes; i++) {
        if (firstByteIndex + i >= data.length) continue;
        final byte currentByte = data[firstByteIndex + i];
        if (i == 0) {
          final int firstByteOffset = start % 8;
          long append = ((((long) currentByte & 0xFF) & (0xFF >>> firstByteOffset)));
          final int lastByteOffset = (start + length) % 8;
          if (i == numInvolvedBytes) {
            append = append >>> (8 - lastByteOffset);
          } else {
            append = append << ((numInvolvedBytes - 1) * 8) + lastByteOffset;
          }
          result = result | append;
          continue;
        }

        if (i == numInvolvedBytes) {
          final int lastByteOffset = (start + length) % 8;
          final int shiftRight = 8 - lastByteOffset;
          long append = ((long) currentByte & 0xFF) >>> shiftRight;
          result = result | append;
          // result = currentByte & ((byte) 0xF << 8 - lastByteOffset);
        } else {
          final int lastByteOffset = (start + length) % 8;
          final int shiftLeft = (i * 8) - (8 - lastByteOffset);
          result = result | ((long) currentByte & 0xFF) << shiftLeft;
        }
      }

      return result;
    }
  }

  enum OperatorType {
    SUM,
    PRODUCT,
    MINIMUM,
    MAXIMUM,
    GREATER_THAN,
    LESS_THAN,
    EQUAL_TO;

    static OperatorType of(final int typeId) {
      return switch (typeId) {
        case 0 -> SUM;
        case 1 -> PRODUCT;
        case 2 -> MINIMUM;
        case 3 -> MAXIMUM;
        case 5 -> GREATER_THAN;
        case 6 -> LESS_THAN;
        case 7 -> EQUAL_TO;
        default -> throw new UnsupportedOperationException();
      };
    }
  }

  interface BitsPacket {
    int packetVersion();

    long versionSum();

    long evaluate();

    static LiteralValue lit(final int packetVersion, final long value) {
      return new LiteralValue(packetVersion, value);
    }

    static Operator op(
        final int packetVersion, final OperatorType type, final BitsPacket... subPackets) {
      return new Operator(packetVersion, type, Arrays.asList(subPackets));
    }

    record LiteralValue(int packetVersion, long value) implements BitsPacket {
      @Override
      public long versionSum() {
        return packetVersion;
      }

      @Override
      public long evaluate() {
        return value;
      }
    }

    record Operator(int packetVersion, OperatorType type, List<BitsPacket> subPackets)
        implements BitsPacket {
      @Override
      public long versionSum() {
        return packetVersion + subPackets.stream().mapToLong(BitsPacket::versionSum).sum();
      }

      @Override
      public long evaluate() {
        return switch (type) {
          case SUM -> subPackets().stream().mapToLong(BitsPacket::evaluate).sum();
          case PRODUCT -> subPackets().stream()
              .mapToLong(BitsPacket::evaluate)
              .reduce((a, b) -> a * b)
              .orElseThrow();
          case MINIMUM -> subPackets().stream().mapToLong(BitsPacket::evaluate).min().orElseThrow();
          case MAXIMUM -> subPackets().stream().mapToLong(BitsPacket::evaluate).max().orElseThrow();
          case GREATER_THAN -> subPackets.get(0).evaluate() > subPackets.get(1).evaluate() ? 1 : 0;
          case LESS_THAN -> subPackets.get(0).evaluate() < subPackets.get(1).evaluate() ? 1 : 0;
          case EQUAL_TO -> subPackets.get(0).evaluate() == subPackets.get(1).evaluate() ? 1 : 0;
        };
      }
    }
  }
}
