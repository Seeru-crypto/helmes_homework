package demo.service.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static demo.model.User.EMAIL_REGEX;

class EmailValidatorTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/validEmails.csv")
  void isEmailValid_ShouldReturnTrueForValidEmails(String email) {
    Assertions.assertTrue(email.matches(EMAIL_REGEX));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/invalidEmails.csv")
  void isEmailValid_ShouldReturnFalseForInvalidEmails(String email) {
    Assertions.assertFalse(email.matches(EMAIL_REGEX));
  }
}