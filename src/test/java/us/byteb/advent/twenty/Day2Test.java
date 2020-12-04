package us.byteb.advent.twenty;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import us.byteb.advent.twenty.Day2.ValidationItem;

class Day2Test {

  @ParameterizedTest
  @ArgumentsSource(InputProvider.class)
  void testPartTwoExamples(final String input, boolean expected) {
    assertEquals(expected, ValidationItem.parse(input).testSecondPolicy());
  }

  static class InputProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of("1-3 a: abcde", true),
          Arguments.of("1-3 b: cdefg", false),
          Arguments.of("2-9 c: ccccccccc", false));
    }
  }
}
