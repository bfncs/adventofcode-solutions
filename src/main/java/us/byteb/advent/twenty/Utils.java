package us.byteb.advent.twenty;

import java.io.File;
import java.nio.file.Files;

public final class Utils {

  public static String readFileFromResources(final String fileName) {
    try {
      final ClassLoader classLoader = Utils.class.getClassLoader();
      final File file = new File(classLoader.getResource(fileName).getFile());
      return new String(Files.readAllBytes(file.toPath()));
    } catch (Exception e) {
      throw new IllegalStateException("Unable to read file " + fileName, e);
    }
  }
}
