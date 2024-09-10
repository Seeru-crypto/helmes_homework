package demo.service.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static demo.model.User.PHONE_NR_REGEX;

class PhoneNumberValidatorTest  {

  @ParameterizedTest
  @CsvFileSource(resources = "/validPhoneNumbers.csv")
  void isPhoneNumberValid_ShouldReturnTrueForValidPhoneNumbers(String phoneNumber) {
    Assertions.assertTrue(phoneNumber.matches(PHONE_NR_REGEX));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/invalidPhoneNumbers.csv")
  void isPhoneNumberValid_ShouldReturnFalseForInvalidPhoneNumbers(String phoneNumber) {
    Assertions.assertFalse(phoneNumber.matches(PHONE_NR_REGEX));
  }
}