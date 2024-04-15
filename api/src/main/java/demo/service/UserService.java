package demo.service;

import demo.exception.BusinessException;
import demo.exception.NotFoundException;
import demo.model.Filter;
import demo.model.Sector;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.UserRepository;
import demo.service.filter.DataTypes;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final ValidationService validationService;
  private final FilterService filterService;
  private final EntityManager entityManager;

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

  public List<User> findAllBySector(Sector existingSector) {
    return userRepository.findAllBySectorsContains(existingSector);
  }

  public List<User> findAllByUserFilter(UserFilter existingFilter) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> itemRoot = criteriaQuery.from(User.class);
     var test2 = generatePredicateFromFilter(existingFilter.getFilters().get(0), criteriaBuilder, itemRoot);

    Predicate predicateEquelsJohn
            = criteriaBuilder.equal(itemRoot.get("name"), "qJack Doesn't");

    Predicate predicateContainsJo
            = criteriaBuilder.like(itemRoot.get("name"), "%Does%");

    Predicate predicateForColor
            = criteriaBuilder.or(predicateEquelsJohn, predicateContainsJo);

//        List<Predicate> test = List.of(predicateContainsJo, predicateEquelsJohn);
    List<Predicate> test = List.of(predicateContainsJo);
    Predicate finalPredicate = null;
    for (Predicate predicate : test) {
      if (finalPredicate == null) {
        finalPredicate = predicate;
      } else {
        finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
      }
    }


//        Predicate predicateForGradeA
//                = criteriaBuilder.equal(itemRoot.get("grade"), "A");
//        Predicate predicateForGradeB
//                = criteriaBuilder.equal(itemRoot.get("grade"), "B");
//        Predicate predicateForGrade
//                = criteriaBuilder.or(predicateForGradeA, predicateForGradeB);

//        Predicate finalPredicate
//                = criteriaBuilder.and(predicateForColor, predicateForGrade);


//        criteriaQuery.where(finalPredicate);

    criteriaQuery.where(finalPredicate);
    List<User> items = entityManager.createQuery(criteriaQuery).getResultList();
//

//        return items;
    return null;
  }


  private Predicate generatePredicateFromFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<User> itemRoot) {
    Predicate predicate = null;

    if (filter.getType() == DataTypes.STRING) {
      // Should be string criteria
      StringCriteria criteria = StringCriteria.valueOf(filter.getCriteria());
      return switch (criteria) {
        case CONTAINS ->
                criteriaBuilder.like(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
        case DOES_NOT_CONTAIN ->
                criteriaBuilder.notLike(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
        case EQUALS -> criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
      };
    }
    return null;
  }


  // name contains A




}
