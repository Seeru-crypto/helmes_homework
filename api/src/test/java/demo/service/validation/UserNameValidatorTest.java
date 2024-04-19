package demo.service.validation;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.user_validator.NameValidator;
import demo.service.validation.user_validator.UserErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.DateCriteria.BEFORE;
import static demo.service.filter.DateCriteria.EQUALS;
import static demo.service.validation.user_validator.UserErrors.NAME_DOESNT_CONTAIN_Q;
import static demo.service.validation.user_validator.UserErrors.NAME_NOT_UNIQUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
class UserNameValidatorTest {

  @Mock
  private UserRepository userRepository;

  private NameValidator userNameValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userNameValidator = new NameValidator(userRepository);
  }

  @ParameterizedTest
  @MethodSource("validUserNames")
  void nameValidation_shouldReturnTrue_whenValidName(String userName) {
    User user = new User().setName(userName);
    when(userRepository.existsByName(userName)).thenReturn(false);

    ValidationResult result = userNameValidator.validate(user);

    assertTrue(result.isValid());
    assertNull(result.getMessage());
  }

  static Stream<String> validUserNames() {
    return Stream.of(
            "validqusername",
            "anotherqexample.com"
    );
  }

  @ParameterizedTest
  @MethodSource("invalidUserNames")
  void nameValidation_shouldReturnFalse_whenInvalidName(UserErrors userErrors, String input) {
    User user = new User().setName(input);
    when(userRepository.existsByName("duplicatequsername")).thenReturn(true);

    ValidationResult result = userNameValidator.validate(user);
    assertFalse(result.isValid());
    assertEquals(userErrors, result.getMessage());
  }

  private static Stream<Arguments> invalidUserNames() {
    return Stream.of(
            Arguments.of(NAME_DOESNT_CONTAIN_Q, "noatsymbol"),
            Arguments.of(NAME_DOESNT_CONTAIN_Q, "invalid.email.com"),
            Arguments.of(NAME_NOT_UNIQUE, "duplicatequsername")
    );
  }
}