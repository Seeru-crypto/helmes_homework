package demo.service.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static demo.model.User.EMAIL_REGEX;

class EmailValidatorTest {

  @ParameterizedTest
  @MethodSource("validEmails")
  void isEmailValid_ShouldReturnTrueForValidEmails(String email) {
    Assertions.assertTrue(email.matches(EMAIL_REGEX));
  }

  static Stream<String> validEmails() {
    return Stream.of(
            "email@gmail.com", "myEmail@outlook.com", "username@domain.com",
            "username@domain.eu",
            "username@domain.co.uk",
            "email@gmail.com",
            "test@domain.com",
            "lastname@domain.com",
            "test.email.with+symbol@domain.com"
    );
  }

  @ParameterizedTest
  @MethodSource("invalidEmails")
  void isEmailValid_ShouldReturnFalseForInvalidEmails(String email) {
    Assertions.assertFalse(email.matches(EMAIL_REGEX));
  }

  static Stream<String> invalidEmails() {
    return Stream.of(
            "tere", " ", "", "email@", "email@.com", "email@gmail", "email@gmail.", "123", "use@123-com",
            "usernamedomain.com",
            "username@domaincom",
            "A@b@c@domain.com",
            "abc\\ is\\”not\\valid@domain.com",
            "a”b(c)d,e:f;gi[j\\k]l@domain.com"
    );
  }
}