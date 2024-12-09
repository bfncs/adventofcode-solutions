package us.byteb.advent.year2022;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Stream;
import us.byteb.advent.year2022.Day07.Command.ChangeDirectory;
import us.byteb.advent.year2022.Day07.Command.ListContents;
import us.byteb.advent.year2022.Day07.FsItem.Directory;
import us.byteb.advent.year2022.Day07.FsItem.File;
import us.byteb.advent.year2022.Day07.FsItemDescription.DirectoryDescription;
import us.byteb.advent.year2022.Day07.FsItemDescription.FileDescription;
import us.byteb.advent.year2022.Day07.TargetDirectory.Parent;
import us.byteb.advent.year2022.Day07.TargetDirectory.Root;
import us.byteb.advent.year2022.Day07.TargetDirectory.Subdirectory;

public class Day07 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day07.txt");

    System.out.println(
        "Part 1: " + sumOfTotalSizesOfDirectoriesBelow100000(determineFilesystem(parse(input))));
    System.out.println(
        "Part 2: " + smallestDirectoryToFreeUpSpace(determineFilesystem(parse(input))).size());
  }

  static List<Command> parse(final String input) {
    final List<Command> commands = new ArrayList<>();

    final List<String> lines = input.lines().toList();
    int currentLine = 0;

    while (currentLine < lines.size() - 1) {
      final String line = lines.get(currentLine);
      if (line.startsWith("$ cd ")) {
        commands.add(new ChangeDirectory(TargetDirectory.parse(line.substring(5))));
        currentLine++;
      } else if (line.equals("$ ls")) {
        String resultLine;
        final Set<FsItemDescription> result = new HashSet<>();
        while (currentLine < lines.size() - 1) {
          resultLine = lines.get(++currentLine);
          if (resultLine.startsWith("$")) {
            break;
          }
          result.add(FsItemDescription.parse(resultLine));
        }
        commands.add(new ListContents(result));
      } else {
        throw new IllegalStateException("Illegal line: " + line);
      }
    }

    return commands;
  }

  static Directory determineFilesystem(final List<Command> commands) {
    final Directory root = Directory.root();
    Directory currentDirectory = root;

    for (final Command command : commands) {
      switch (command) {
        case ChangeDirectory cd ->
            currentDirectory =
                switch (cd.target()) {
                  case Root ignored -> root;
                  case Parent ignored -> currentDirectory.parent();
                  case Subdirectory subdirectory ->
                      (Directory) currentDirectory.child(subdirectory.name()).orElseThrow();
                };
        case ListContents ls -> {
          for (final FsItemDescription lsResult : ls.result()) {
            switch (lsResult) {
              case FileDescription file ->
                  currentDirectory.addChild(new File(file.name(), file.size()));
              case DirectoryDescription directory ->
                  currentDirectory.addChild(
                      Directory.directory(directory.name(), currentDirectory));
            }
          }
        }
      }
    }

    return root;
  }

  static Set<Directory> findDirectories(final Directory directory) {
    final Set<Directory> result = new HashSet<>();
    final Queue<Directory> queue = new LinkedList<>(Set.of(directory));

    while (!queue.isEmpty()) {
      final Directory currentDirectory = queue.remove();
      result.add(currentDirectory);
      currentDirectory.children().stream()
          .flatMap(item -> item instanceof Directory d ? Stream.of(d) : Stream.empty())
          .forEach(queue::add);
    }

    return result;
  }

  static long sumOfTotalSizesOfDirectoriesBelow100000(final Directory directory) {
    return findDirectories(directory).stream()
        .mapToLong(Directory::size)
        .filter(size -> size <= 100000)
        .sum();
  }

  static Directory smallestDirectoryToFreeUpSpace(final Directory directory) {
    final long minSizeToFreeUp = 30_000_000L - (70_000_000L - directory.size());
    return findDirectories(directory).stream()
        .filter(dir -> dir.size() > minSizeToFreeUp)
        .min(Comparator.comparing(Directory::size))
        .orElseThrow();
  }

  sealed interface Command {
    record ChangeDirectory(TargetDirectory target) implements Command {}

    record ListContents(Set<FsItemDescription> result) implements Command {}
  }

  sealed interface TargetDirectory {

    static TargetDirectory parse(final String input) {
      return switch (input) {
        case ".." -> new Parent();
        case "/" -> new Root();
        default -> new Subdirectory(input);
      };
    }

    record Subdirectory(String name) implements TargetDirectory {}

    record Parent() implements TargetDirectory {}

    record Root() implements TargetDirectory {}
  }

  sealed interface FsItemDescription {
    static FsItemDescription parse(final String input) {
      if (input.startsWith("dir ")) {
        return new DirectoryDescription(input.substring(4));
      }

      final String[] parts = input.split(" ");
      if (parts.length != 2) {
        throw new IllegalStateException("Illegal line: " + input);
      }

      return new FileDescription(parts[1], Long.parseLong(parts[0]));
    }

    record FileDescription(String name, long size) implements FsItemDescription {}

    record DirectoryDescription(String name) implements FsItemDescription {}
  }

  sealed interface FsItem {

    String name();

    long size();

    record File(String name, long size) implements FsItem {}

    final class Directory implements FsItem {
      private final String name;
      private final Directory parent;
      private final Map<String, FsItem> content;

      public static Directory root() {
        return new Directory("/", null);
      }

      public static Directory directory(final String name, final Directory parent) {
        return new Directory(name, parent);
      }

      private Directory(final String name, final Directory parent) {
        this.name = name;
        this.parent = parent;
        this.content = new HashMap<>();
      }

      @Override
      public String name() {
        return name;
      }

      public Directory parent() {
        return parent;
      }

      public void addChild(final FsItem item) {
        content.put(item.name(), item);
      }

      public Optional<FsItem> child(final String name) {
        return Optional.ofNullable(content.get(name));
      }

      public Collection<FsItem> children() {
        return content.values();
      }

      public long size() {
        return content.values().stream().mapToLong(FsItem::size).sum();
      }

      @Override
      public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
      }

      @Override
      public boolean equals(final Object o) {
        return reflectionEquals(this, o);
      }

      @Override
      public int hashCode() {
        return reflectionHashCode(this);
      }
    }
  }
}
