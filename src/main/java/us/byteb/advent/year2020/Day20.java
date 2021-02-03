package us.byteb.advent.year2020;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Day20 {

  public static void main(String[] args) {
    final Set<Tile> tiles = parseTiles(readFileFromResources("year2020/day20.txt"));

    final long resultPart1 =
        findEdges(tiles).stream().mapToLong(Tile::id).reduce(1, (a, b) -> a * b);
    System.out.println("Part 1: " + resultPart1);
  }

  public static Set<Tile> parseTiles(final String input) {
    final List<String> lines = input.lines().collect(toList());
    final Set<Tile> tileSet = new HashSet<>();
    StringBuilder curTile = new StringBuilder();
    for (int i = 0; i < lines.size(); i++) {
      final String line = lines.get(i);
      if ((i == (lines.size() - 1))) {
        curTile.append(line);
        tileSet.add(Tile.parse(curTile.toString()));
        curTile = new StringBuilder();
      } else if (isBlank(line)) {
        tileSet.add(Tile.parse(curTile.toString()));
        curTile = new StringBuilder();
      } else {
        curTile.append(line).append("\n");
      }
    }

    return tileSet;
  }

  public static Set<Tile> findEdges(final Set<Tile> tiles) {
    final List<Integer> forwardEdgeHashes =
        tiles.stream().flatMap(tile -> tile.edgeHashes().stream()).collect(toList());

    final List<Integer> reverseEdgeHashes =
        tiles.stream()
            .flatMap(
                tile ->
                    tile.edgesNormalized().stream()
                        .map(Tile::reverseEdge)
                        .map(Tile::edgeHash)
                        .collect(toList())
                        .stream())
            .collect(toList());

    return tiles.stream()
        .filter(tile -> getNumMatchingEdges(tile, forwardEdgeHashes, reverseEdgeHashes) == 2)
        .collect(toSet());
  }

  private static long getNumMatchingEdges(
      final Tile tile,
      final List<Integer> forwardEdgeHashes,
      final List<Integer> reverseEdgeHashes) {
    return tile.edgeHashes().stream()
        .filter(
            edgeHash -> {
              final long forwardMatches =
                  forwardEdgeHashes.stream().filter(eh -> eh.equals(edgeHash)).count() - 1;
              final long reversedMatches =
                  reverseEdgeHashes.stream().filter(eh -> eh.equals(edgeHash)).count();

              return (forwardMatches + reversedMatches) > 0;
            })
        .count();
  }

  public static final record Tile(int id, boolean[][] data) {
    public static Tile parse(final String input) {
      final List<String> lines = input.lines().collect(toList());

      final String firstLine = lines.get(0);
      final int id = Integer.parseInt(firstLine.substring(5, firstLine.length() - 1));

      final boolean[][] data = new boolean[lines.size() - 1][];
      final List<String> bitmapLines = lines.subList(1, lines.size());
      for (int y = 0; y < bitmapLines.size(); y++) {
        final String line = bitmapLines.get(y);
        data[y] = new boolean[line.length()];
        for (int x = 0; x < line.length(); x++) {
          data[y][x] = line.charAt(x) == '#';
        }
      }

      return new Tile(id, data);
    }

    public boolean value(final int y, final int x) {
      return data[y][x];
    }

    public List<boolean[]> edgesNormalized() {
      final List<boolean[]> edges = new ArrayList<>();
      edges.add(data[0]);
      edges.add(column(data[0].length - 1));
      edges.add(reverseEdge(data[data.length - 1]));
      edges.add(reverseEdge(column(0)));

      return edges;
    }

    public List<Integer> edgeHashes() {
      return edgesNormalized().stream().map(Tile::edgeHash).collect(toList());
    }

    private boolean[] column(final int col) {
      final boolean[] result = new boolean[data.length];
      for (int i = 0; i < data.length; i++) {
        result[i] = data[i][col];
      }

      return result;
    }

    static boolean[] reverseEdge(final boolean[] data) {
      final boolean[] result = new boolean[data.length];
      for (int i = 0; i < data.length; i++) {
        result[data.length - 1 - i] = data[i];
      }

      return result;
    }

    public static int edgeHash(final boolean[] edge) {
      int hash = 1;
      for (final boolean b : edge) {
        hash = (hash << 1) + (b ? 1 : 0);
      }

      return hash;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      final Tile tile = (Tile) o;
      if (tile.id != this.id) return false;
      if (tile.data.length != this.data.length) return false;

      for (int y = 0; y < tile.data.length; y++) {
        if (!Arrays.equals(tile.data[y], this.data[y])) {
          return false;
        }
      }
      return true;
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
      return "Tile-" + id;
    }
  }
}
