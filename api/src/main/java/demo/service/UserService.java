package demo.service;

import demo.exception.BusinessException;
import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.UserRepository;
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
    private final FilterService filterService;
//    private final EntityManager entityManager;

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
        validationService.validateEntity(entity, validationService.getUserValidator());

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


    public List<User> findAllByFilter(Long userFilterId) {
        UserFilter userFilter = filterService.findById(userFilterId);
        // user name contains A
        // user dob later than 1995

        return null;
    }

    public List<User> findAllBySector(Sector existingSector) {
        return userRepository.findAllBySectorsContains(existingSector);
    }

    public List<User> findAllByUserFilter(UserFilter existingFilter) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> itemRoot = criteriaQuery.from(User.class);
//
//        Predicate predicateForBlueColor
//                = criteriaBuilder.equal(itemRoot.get("color"), "blue");
//        Predicate predicateForRedColor
//                = criteriaBuilder.equal(itemRoot.get("color"), "red");
//        Predicate predicateForColor
//                = criteriaBuilder.or(predicateForBlueColor, predicateForRedColor);
//
//        Predicate predicateForGradeA
//                = criteriaBuilder.equal(itemRoot.get("grade"), "A");
//        Predicate predicateForGradeB
//                = criteriaBuilder.equal(itemRoot.get("grade"), "B");
//        Predicate predicateForGrade
//                = criteriaBuilder.or(predicateForGradeA, predicateForGradeB);
//
//        Predicate finalPredicate
//                = criteriaBuilder.and(predicateForColor, predicateForGrade);
//        criteriaQuery.where(finalPredicate);
//        List<User> items = entityManager.createQuery(criteriaQuery).getResultList();
//

//        return items;
        return null;
    }
}
