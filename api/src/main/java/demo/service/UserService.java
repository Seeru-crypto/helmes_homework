package demo.service;

import demo.exception.BusinessException;
import demo.model.Sector;
import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static demo.exception.BusinessException.USER_NAME_EXISTS;
import static demo.exception.BusinessException.USER_NAME_VALIDATION_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final SectorService sectorService;

  private static final String USER_ID = "id";
  private static final int DEFAULT_PAGE_NUMBER = 0;
  private static final int DEFAULT_SIZE_OF_PAGE = 10;
  private final List<Validator> validators;

  protected void validateSaveData_v2(User user) {
    ValidationResult validationResult = validators.stream()
            .map(validator -> validator.validate(user))
            .filter(result -> !result.isResult())
            .findFirst()
            .orElse(new ValidationResult().setResult(true)); // If no validation failure, return a successful result

    if (!validationResult.isResult()) {
      log.warn("user validation failed: {}", validationResult.getMessage());
      throw new BusinessException(USER_NAME_VALIDATION_FAILED) {
        // Override getMessage() to provide a custom error message
        @Override
        public String getMessage() {
          return validationResult.getMessage();
        }
      };
    }
  }

  protected void validateUpdateUserData(User user, Long userId) {
    validateSaveData_v2(user);
    User existingUSer = userRepository.getReferenceById(userId);

    if (userRepository.existsByName(user.getName()) && !existingUSer.getName().equals(user.getName())) {
      throw new BusinessException(USER_NAME_EXISTS) {
      };
    }
  }

  @Transactional
  public User save(User user, List<String> sectorNames) {
    validateSaveData_v2(user);
    List<Sector> sectors = sectorNames.stream().map(sectorService::findByName).toList();
    user.setSectors(sectors);
    return userRepository.save(user);
  }

  public Page<User> findAll(String sortBy, Integer pageNumber, Integer sizeOfPage) {
    if (pageNumber == null) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (sizeOfPage == null) {
      sizeOfPage = DEFAULT_SIZE_OF_PAGE;
    }
    if (sortBy == null || sortBy.equals("")) {
      sortBy = USER_ID;
    }
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage, Sort.by(Sort.Direction.ASC, sortBy));
    return userRepository.findAll(pageable);
  }

  @Transactional
  public User update(User entity, List<String> sectorNames, Long userId) {
    validateUpdateUserData(entity, userId);
    User user = userRepository.getReferenceById(userId);
    List<Sector> sectors = sectorNames.stream().map(sectorService::findByName).toList();
    return user
            .setName(entity.getName())
            .setSectors(sectors);
  }
}
