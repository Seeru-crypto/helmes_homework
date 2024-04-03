package demo.service;

import demo.exception.BusinessException;
import demo.model.Sector;
import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
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
  private final SectorService sectorService;

  private static final String USER_ID = "id";
  private static final int DEFAULT_PAGE_NUMBER = 0;
  private static final int DEFAULT_SIZE_OF_PAGE = 10;
  private final List<UserValidator> userValidators;

  protected void validateSaveData_v2(User user) {
    ValidationResult validationResult = userValidators.stream()
            .map(userValidator -> userValidator.validate(user))
            .filter(result -> !result.isValid())
            .findFirst()
            .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

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
    validateSaveData_v2(user);
//    List<Sector> sectors = sectorNames.stream().map(sectorService::findByName).toList();
    return userRepository.save(user);
  }

  public Page<User> findAll(String sortBy, Integer pageNumber, Integer sizeOfPage) {
    if (pageNumber == null) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (sizeOfPage == null) {
      sizeOfPage = DEFAULT_SIZE_OF_PAGE;
    }
    if (sortBy == null || sortBy.isEmpty()) {
      sortBy = USER_ID;
    }
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage, Sort.by(Sort.Direction.ASC, sortBy));
    return userRepository.findAll(pageable);
  }

  @Transactional
  public User update(User entity, Long userId) {
    userRepository.findById(userId)
            .orElseThrow(() -> {
              log.warn("update validation exception: given user does not exist {}", userId);
              return new BusinessException("given user does not exist"){};
            });
    validateSaveData_v2(entity);

    return userRepository.save(entity);
  }
}
