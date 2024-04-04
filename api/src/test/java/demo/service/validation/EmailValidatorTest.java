package demo.service.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static demo.model.User.EMAIL_REGEX;
import static demo.model.User.PHONE_NR_REGEX;

class EmailValidatorTest {

  @ParameterizedTest
  @ValueSource(strings = {"email@gmail.com", "myEmail@outlook.com", "username@domain.com",
          "username@domain.eu",
          "username@domain.co.uk",
          "email@gmail.com",
          "test@domain.com",
          "lastname@domain.com",
          "test.email.with+symbol@domain.com"})
  void isEmailValid_ShouldReturnTrueForValidEmails(String email) {
    Assertions.assertTrue(email.matches(EMAIL_REGEX));
  }

  @ParameterizedTest
  @ValueSource(strings = {"tere", " ", "", "email@", "email@.com", "email@gmail", "email@gmail.", "123", "use@123-com",
          "usernamedomain.com",
          "username@domaincom",
          "A@b@c@domain.com",
          "abc\\ is\\”not\\valid@domain.com",
          "a”b(c)d,e:f;gi[j\\k]l@domain.com"})
  void isEmailValid_ShouldReturnFalseForInvalidEmails(String email) {
    Assertions.assertFalse(email.matches(EMAIL_REGEX));
  }
}