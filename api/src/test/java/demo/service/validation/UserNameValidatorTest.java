package demo.service.validation;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.user_validator.NameValidator;
import demo.service.validation.user_validator.UserErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
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
  @CsvFileSource(resources = "/validUserNames.csv")
  void nameValidation_shouldReturnTrue_whenValidName(String userName) {
    User user = new User().setName(userName);
    when(userRepository.existsByName(userName)).thenReturn(false);

    ValidationResult result = userNameValidator.validate(user);

    assertTrue(result.isValid());
    assertNull(result.getMessage());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/inValidUserNames.csv")
  void nameValidation_shouldReturnFalse_whenInvalidName(UserErrors userErrors, String input) {
    User user = new User().setName(input);
    when(userRepository.existsByName("duplicatequsername")).thenReturn(true);

    ValidationResult result = userNameValidator.validate(user);
    assertFalse(result.isValid());
    assertEquals(userErrors, result.getMessage());
  }
}