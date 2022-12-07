package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day07.*;
import static us.byteb.advent.year2022.Day07.FsItem.Directory.directory;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2022.Day07.Command.ChangeDirectory;
import us.byteb.advent.year2022.Day07.Command.ListContents;
import us.byteb.advent.year2022.Day07.FsItem.Directory;
import us.byteb.advent.year2022.Day07.FsItem.File;
import us.byteb.advent.year2022.Day07.FsItemDescription.DirectoryDescription;
import us.byteb.advent.year2022.Day07.FsItemDescription.FileDescription;
import us.byteb.advent.year2022.Day07.TargetDirectory.Root;
import us.byteb.advent.year2022.Day07.TargetDirectory.Subdirectory;

class Day07Test {

  private static final String EXAMPLE_DATA =
      """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
            """;
  private static final Root ROOT = new Root();
  private static final Day07.TargetDirectory.Parent PARENT = new Day07.TargetDirectory.Parent();

  @Test
  void parseInput() {
    assertEquals(
        List.of(
            new ChangeDirectory(ROOT),
            new ListContents(
                Set.of(
                    new DirectoryDescription("a"),
                    new FileDescription("b.txt", 14848514L),
                    new FileDescription("c.dat", 8504156L),
                    new DirectoryDescription("d"))),
            new ChangeDirectory(new Subdirectory("a")),
            new ListContents(
                Set.of(
                    new DirectoryDescription("e"),
                    new FileDescription("f", 29116L),
                    new FileDescription("g", 2557L),
                    new FileDescription("h.lst", 62596L))),
            new ChangeDirectory(new Subdirectory("e")),
            new ListContents(Set.of(new FileDescription("i", 584L))),
            new ChangeDirectory(PARENT),
            new ChangeDirectory(PARENT),
            new ChangeDirectory(new Subdirectory("d")),
            new ListContents(
                Set.of(
                    new FileDescription("j", 4060174L),
                    new FileDescription("d.log", 8033020L),
                    new FileDescription("d.ext", 5626152L),
                    new FileDescription("k", 7214296L)))),
        parse(EXAMPLE_DATA));
  }

  @Test
  void buildFilesystemTree() {
    final Directory root = Directory.root();

    final Directory dirA = directory("a", root);

    final Directory dirE = directory("e", dirA);
    dirE.addChild(new File("i", 584L));
    dirA.addChild(dirE);

    dirA.addChild(new File("f", 29116L));
    dirA.addChild(new File("g", 2557L));
    dirA.addChild(new File("h.lst", 62596L));

    root.addChild(dirA);
    root.addChild(new File("b.txt", 14848514L));
    root.addChild(new File("c.dat", 8504156L));

    final Directory dirD = directory("d", root);
    dirD.addChild(new File("j", 4060174L));
    dirD.addChild(new File("d.log", 8033020L));
    dirD.addChild(new File("d.ext", 5626152L));
    dirD.addChild(new File("k", 7214296L));
    root.addChild(dirD);

    assertEquals(root, determineFilesystem(parse(EXAMPLE_DATA)));
  }

  @Test
  void partOneExample() {
    final Directory root = determineFilesystem(parse(EXAMPLE_DATA));
    final long result = sumOfTotalSizesOfDirectoriesBelow100000(root);
    assertEquals(95437L, result);
  }
}
