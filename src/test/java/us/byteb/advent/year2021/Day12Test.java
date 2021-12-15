package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day12.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class Day12Test {

  @Test
  void part1Example1() {
    final List<Edge> edges =
        parseInput(
            """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """);
    final Set<List<Cave>> expectedPaths =
        parsePaths(
            """
                    start,A,b,A,c,A,end
                    start,A,b,A,end
                    start,A,b,end
                    start,A,c,A,b,A,end
                    start,A,c,A,b,end
                    start,A,c,A,end
                    start,A,end
                    start,b,A,c,A,end
                    start,b,A,end
                    start,b,end
                    """);
    assertEquals(expectedPaths, allPathsVisitingSmallCavesAtMostOnce(edges));
  }

  @Test
  void part1Example2() {
    final List<Edge> edges =
        parseInput(
            """
                    dc-end
                    HN-start
                    start-kj
                    dc-start
                    dc-HN
                    LN-dc
                    HN-end
                    kj-sa
                    kj-HN
                    kj-dc
                    """);
    final Set<List<Cave>> expectedPaths =
        parsePaths(
            """
                          start,HN,dc,HN,end
                          start,HN,dc,HN,kj,HN,end
                          start,HN,dc,end
                          start,HN,dc,kj,HN,end
                          start,HN,end
                          start,HN,kj,HN,dc,HN,end
                          start,HN,kj,HN,dc,end
                          start,HN,kj,HN,end
                          start,HN,kj,dc,HN,end
                          start,HN,kj,dc,end
                          start,dc,HN,end
                          start,dc,HN,kj,HN,end
                          start,dc,end
                          start,dc,kj,HN,end
                          start,kj,HN,dc,HN,end
                          start,kj,HN,dc,end
                          start,kj,HN,end
                          start,kj,dc,HN,end
                          start,kj,dc,end
                          """);
    assertEquals(expectedPaths, allPathsVisitingSmallCavesAtMostOnce(edges));
  }

  @Test
  void part1Example3() {
    final List<Edge> edges =
        parseInput(
            """
                    fs-end
                    he-DX
                    fs-he
                    start-DX
                    pj-DX
                    end-zg
                    zg-sl
                    zg-pj
                    pj-he
                    RW-he
                    fs-DX
                    pj-RW
                    zg-RW
                    start-pj
                    he-WI
                    zg-he
                    pj-fs
                    start-RW
                    """);
    assertEquals(226, allPathsVisitingSmallCavesAtMostOnce(edges).size());
  }

  private static Set<List<Cave>> parsePaths(final String input) {
    return input
        .lines()
        .map(line -> Arrays.stream(line.split(",")).map(Cave::new).toList())
        .collect(Collectors.toSet());
  }
}
