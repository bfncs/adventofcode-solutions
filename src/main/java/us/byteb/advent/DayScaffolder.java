package us.byteb.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DayScaffolder {

  private static final String MAIN_TEMPLATE =
      """
      package us.byteb.advent.year{{YEAR}};

      import static us.byteb.advent.Utils.readFileFromResources;

      public class Day{{DAY}} {

        public static void main(String[] args) {
          final String input = readFileFromResources("year{{YEAR}}/day{{DAY}}.txt");
          System.out.println("Part 1: " + "");
          System.out.println("Part 2: " + "");
        }

      }
      """;

  private static final String TEST_TEMPLATE =
      """
      package us.byteb.advent.year{{YEAR}};

      import static org.junit.jupiter.api.Assertions.*;
      import org.junit.jupiter.api.*;

      class Day{{DAY}}Test {

        @Test
        void testPart1() {
          assertEquals("", null);
        }

        @Test
        void testPart2() {
          assertEquals("", null);
        }
      }
    """;

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: mvn exec:java@scaffold-day -Dexec.args=\"YYYY DD\"");
      System.exit(1);
    }

    final String year = args[0];
    final String day = String.format("%02d", Integer.parseInt(args[1]));

    System.out.printf("🎁 Scaffolding Advent of Code: Year %s, Day %s%n", year, day);

    try {
      createMainClass(year, day);
      createTestClass(year, day);
      createResourceFile(year, day);
      System.out.println("✅ Done!");
    } catch (Exception e) {
      System.err.println("❌ Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void createMainClass(String year, String day) throws IOException {
    final String content = MAIN_TEMPLATE.replace("{{YEAR}}", year).replace("{{DAY}}", day);

    final Path path =
        Paths.get("src/main/java", "us/byteb/advent", "year" + year, "Day" + day + ".java");
    writeFile(path, content);
  }

  private static void createTestClass(String year, String day) throws IOException {
    final String content = TEST_TEMPLATE.replace("{{YEAR}}", year).replace("{{DAY}}", day);

    final Path path =
        Paths.get("src/test/java", "us/byteb/advent", "year" + year, "Day" + day + "Test.java");
    writeFile(path, content);
  }

  private static void createResourceFile(String year, String day) throws IOException {
    final Path path = Paths.get("src/main/resources", "year" + year, "day" + day + ".txt");
    if (!Files.exists(path)) {
      Files.createDirectories(path.getParent());
      Files.createFile(path);
      System.out.println("   Created resource: " + path);
    } else {
      System.out.println("   Skipped resource (exists): " + path);
    }
  }

  private static void writeFile(Path path, String content) throws IOException {
    if (Files.exists(path)) {
      System.out.println("   Skipped (exists): " + path);
      return;
    }
    Files.createDirectories(path.getParent());
    Files.writeString(
        path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    System.out.println("   Created: " + path);
  }
}
