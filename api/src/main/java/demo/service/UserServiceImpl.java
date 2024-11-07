package demo.service;

import demo.exception.NotFoundException;
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
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final FilteringLogicService filteringLogicService;

    @Override
    @Transactional
    public User save(User user) {
        validationService.validateEntity(user, validationService.getUserValidator());
        return userRepository.save(user);
    }

    protected List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        validationService.validateEntity(pageable, validationService.getPageableValidator());
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", id);
                    return new NotFoundException("User not found") {
                    };
                });
    }

    @Override
    @Transactional
    public User update(User entity, UUID userId) {
        User existingUser = findById(userId);
        validationService.validateEntity(entity, validationService.getUserValidator());

        return existingUser
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPhoneNumber(entity.getPhoneNumber())
                .setSectors(entity.getSectors());
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found: {}", id);
            throw new NotFoundException("User not found") {
            };
        }
        userRepository.deleteById(id);
    }

    @Override
    public void removeSectorFromAllUsers(Long sectorId) {
        userRepository
                .findAllBySectorsContains(sectorId)
                .forEach(user ->
                        user.removeSector(sectorId));
    }

    @Override
    public List<User> findAllBySector(Long sectorId) {
        return userRepository.findAllBySectorsContains(sectorId);
    }

    public List<User> findAllByUserFilter(UserFilter existingFilter) {
        return filteringLogicService.findAllByFilter(existingFilter, User.class);
    }
}
