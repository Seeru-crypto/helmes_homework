package demo.service;

import demo.exception.BusinessException;
import demo.model.PageableProps;
import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.pageable_validator.PageableValidator;
import demo.service.validation.user_validator.UserIdValidator;
import demo.service.validation.user_validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  private final List<UserValidator> userValidators;
  private final List<UserIdValidator> userIdValidators;
  private final List<PageableValidator> pageableValidators;

    protected void validateUserData(User user) {
    ValidationResult validationResult = userValidators.stream()
            .map(userValidator -> userValidator.validate(user))
            .filter(result -> !result.isValid())
            .findFirst()
            .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

    validationCleanup(validationResult);
  }

  protected void validateUserId(Long id) {
    ValidationResult validationResult = userIdValidators.stream()
            .map(userValidator -> userValidator.validate(id))
            .filter(result -> !result.isValid())
            .findFirst()
            .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

    validationCleanup(validationResult);
  }

  protected void validatePageableProps(PageableProps pageableProps) {
    ValidationResult validationResult = pageableValidators.stream()
            .map(userValidator -> userValidator.validate(pageableProps))
            .filter(result -> !result.isValid())
            .findFirst()
            .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

    validationCleanup(validationResult);
  }

  private void validationCleanup(ValidationResult validationResult) {
    if (!validationResult.isValid()) {
      log.warn("user validation failed: {}", validationResult.getMessage());
      throw new BusinessException("DEFAULT_ERROR") {
        // Override getMessage() to provide a custom error message
        @Override
        public String getMessage() {
          return validationResult.getMessage().getKood();
        }
      };
    }
  }

  @Transactional
  public User save(User user) {
    validateUserData(user);
    return userRepository.save(user);
  }

  public Page<User> findAll(PageableProps pageableProps) {
    validatePageableProps(pageableProps);

    Pageable pageable = PageRequest.of(pageableProps.getPageNumber(), pageableProps.getSizeOfPage(), Sort.by(Sort.Direction.ASC, pageableProps.getSortBy()));
    return userRepository.findAll(pageable);

  }

  @Transactional
  public User update(User entity, Long userId) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> {
              log.warn("update validation exception: given user does not exist {}", userId);
              return new BusinessException("given user does not exist") {
              };
            });
    validateUserData(entity);

    return existingUser
            .setName(entity.getName())
            .setEmail(entity.getEmail())
            .setPhoneNumber(entity.getPhoneNumber())
            .setSectors(entity.getSectors());
  }

  public void delete(Long id) {
    validateUserId(id);
    userRepository.deleteById(id);
  }
}
