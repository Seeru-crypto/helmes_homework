package demo.service.validation;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.UserValidator.UserNameUserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
class UserNameValidatorTest {

  @Mock
  private UserRepository userRepository;

  private UserNameUserValidator userNameValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userNameValidator = new UserNameUserValidator(userRepository);
  }

  @Test
  void testValidate_ValidUser() {
    User user = new User().setName("valid@username");
    when(userRepository.existsByName(anyString())).thenReturn(false);

    ValidationResult result = userNameValidator.validate(user);

    assertTrue(result.isResult());
    assertNull(result.getMessage());
    verify(userRepository, times(1)).existsByName("valid@username");
  }

  @Test
  void testValidate_NonUniqueName() {
    User user = new User().setName("duplicate@username");
    when(userRepository.existsByName(anyString())).thenReturn(true);

    ValidationResult result = userNameValidator.validate(user);

    assertFalse(result.isResult());
    assertEquals("Name is not unique", result.getMessage());
    verify(userRepository, times(1)).existsByName("duplicate@username");
  }

  @ParameterizedTest
  @ValueSource(strings = {"valid@username", "another@example.com"})
  void testValidate_ValidUserName(String userName) {
    User user = new User().setName(userName);
    when(userRepository.existsByName(userName)).thenReturn(false);

    ValidationResult result = userNameValidator.validate(user);

    assertTrue(result.isResult());
    assertEquals(null, result.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"noatsymbol", "invalid.email.com"})
  void testValidate_InvalidUserName(String userName) {
    User user = new User().setName(userName);

    ValidationResult result = userNameValidator.validate(user);

    assertFalse(result.isResult());
    assertEquals("does not contain @", result.getMessage());
  }
}