package demo.service.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static demo.model.User.PHONE_NR_REGEX;

class PhoneNumberValidatorTest  {

  @ParameterizedTest
  @MethodSource("validPhoneNumbers")
  void isPhoneNumberValid_ShouldReturnTrueForValidPhoneNumbers(String phoneNumber) {
    Assertions.assertTrue(phoneNumber.matches(PHONE_NR_REGEX));
  }

  static Stream<String> validPhoneNumbers() {
    return Stream.of(
            "+372 1234567", "+123 12345678", "+44 1234567890", "+1 1234567890", "+123 123456789"
    );
  }

  @ParameterizedTest
  @MethodSource("invalidPhoneNumbers")
  void isPhoneNumberValid_ShouldReturnFalseForInvalidPhoneNumbers(String phoneNumber) {
    Assertions.assertFalse(phoneNumber.matches(PHONE_NR_REGEX));
  }

  static Stream<String> invalidPhoneNumbers() {
    return Stream.of(
            "1234567", "+44 12345678901", "+1 12345678901", "+1 123 456 78901", "+1 123-456-78901"
    );
  }
}