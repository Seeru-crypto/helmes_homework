package demo.service;

import demo.exception.BusinessException;
import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.UserRepository;
import demo.service.filter.FilteringLogicService;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final FilteringLogicService filteringLogicService;

    @Transactional
    public User save(User user) {
        if (user.getId() != null && !userRepository.existsById(user.getId())) {
            log.warn("User with given ID already exists: {}", user.getEmail());
            throw new BusinessException("User with email already exists") {
            };
        }

        validationService.validateEntity(user, validationService.getUserValidator());
        return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        validationService.validateEntity(pageable, validationService.getPageableValidator());
        return userRepository.findAll(pageable);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", id);
                    return new NotFoundException("User not found") {
                    };
                });
    }

    @Transactional
    public User update(User entity, UUID userId) {
        User existingUser = findById(userId);
        // TODO: Add Update specific validator
        validationService.validateEntity(entity.setId(userId), validationService.getUserValidator());

        return existingUser
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPhoneNumber(entity.getPhoneNumber())
                .setSectors(entity.getSectors());
    }

    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found: {}", id);
            throw new NotFoundException("User not found") {
            };
        }
        userRepository.deleteById(id);
    }

    public void removeSectorFromAllUsers(Sector sector) {
        userRepository
                .findAllBySectorsContains(sector)
                .forEach(user ->
                        user.removeSector(sector));
    }

    public List<User> findAllBySector(Sector existingSector) {
        return userRepository.findAllBySectorsContains(existingSector);
    }

    public List<User> findAllByUserFilter(UserFilter existingFilter) {
        return filteringLogicService.findAllByFilter(existingFilter, User.class);
    }
}
