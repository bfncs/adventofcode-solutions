package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2024.Day09.BlockRange.free;

import java.util.*;

public class Day09 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day09.txt");
    System.out.println("Part 1: " + checksum(compact(parseInput(input))));
  }

  public static List<BlockRange> parseInput(final String input) {
    final List<BlockRange> blockRanges = new ArrayList<>();
    for (int i = 0; i < input.length(); i++) {
      final Content content = i % 2 == 0 ? new Content.Id(i / 2) : new Content.Free();
      final int length = Integer.parseInt(input.substring(i, i + 1));
      blockRanges.add(new BlockRange(content, length));
    }

    return blockRanges;
  }

  public static List<BlockRange> compact(final List<BlockRange> input) {
    final List<BlockRange> result = new LinkedList<>(input);
    while (true) {
      BlockRange toBeMoved = findRightmostContentBlockPosition(result);
      while (toBeMoved.length() > 0) {
        for (int i = 0; i < result.size(); i++) {
          final BlockRange currentBlock = result.get(i);
          if (currentBlock.content() == toBeMoved.content()) {
            if (toBeMoved.length() < currentBlock.length()) {
              result.set(i, toBeMoved);
            }
            return mergeContiguousFreeBlocks(result);
          }
          if (currentBlock.content() instanceof Content.Free) {
            if (toBeMoved.length() >= currentBlock.length()) {
              result.set(i, toBeMoved.withLength(currentBlock.length()));
              toBeMoved = toBeMoved.withLength(toBeMoved.length() - currentBlock.length());
            } else {
              for (int j = result.size() - 1; j >= i; j--) {
                if (result.get(j).content().equals(toBeMoved.content)) {
                  result.set(j, free(result.get(j).length()));
                }
              }
              result.remove(i);
              result.add(i, free(currentBlock.length() - toBeMoved.length()));
              result.add(i, toBeMoved);
              toBeMoved = toBeMoved.withLength(0);
              break;
            }
          }
        }
      }
    }
  }

  public static long checksum(final List<BlockRange> input) {
    long sum = 0;
    long blockStart = 0;
    for (final BlockRange block : input) {
      if (!(block.content() instanceof Content.Id(int id))) {
        continue;
      }

      for (int i = 0; i < block.length(); i++) {
        final long res = (blockStart + i) * id;
        sum += res;
      }
      blockStart += block.length();
    }

    return sum;
  }

  private static List<BlockRange> mergeContiguousFreeBlocks(final List<BlockRange> input) {
    final LinkedList<BlockRange> result = new LinkedList<>(input);
    for (int i = input.size() - 2; i >= 0; i--) {
      if (result.get(i).content() instanceof Content.Free
          && result.get(i + 1).content() instanceof Content.Free) {
        result.set(i, free(result.get(i).length() + result.get(i + 1).length()));
        result.remove(i + 1);
      }
    }
    return result;
  }

  private static BlockRange findRightmostContentBlockPosition(final List<BlockRange> result) {
    for (int i = result.size() - 1; i >= 0; i--) {
      if (result.get(i).content() instanceof Content.Id) {
        return result.get(i);
      }
    }
    throw new IllegalStateException();
  }

  public record BlockRange(Content content, int length) {
    public static BlockRange content(int id, int length) {
      return new BlockRange(new Content.Id(id), length);
    }

    public static BlockRange free(int length) {
      return new BlockRange(new Content.Free(), length);
    }

    public BlockRange withLength(int length) {
      return new BlockRange(content, length);
    }
  }

  public sealed interface Content {
    record Id(int value) implements Content {}

    record Free() implements Content {}
  }
}
