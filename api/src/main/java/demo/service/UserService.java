package demo.service;

import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Transactional
    public User save(User user) {
        validationService.validateEntity(user, validationService.getUserValidator());
        return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        validationService.validateEntity(pageable, validationService.getPageableValidator());
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", id);
                    return new NotFoundException("User not found") {
                    };
                });
    }

    @Transactional
    public User update(User entity, Long userId) {
        User existingUser = findById(userId);
        validationService.validateEntity(entity, validationService.getUserValidator());

        return existingUser
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPhoneNumber(entity.getPhoneNumber())
                .setSectors(entity.getSectors());
    }

    public void delete(Long id) {
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
}
