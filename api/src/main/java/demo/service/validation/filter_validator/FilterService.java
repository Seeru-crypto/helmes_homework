package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.controller.dto.FilterOptionsDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.FilterRepository;
import demo.repository.UserFilterRepository;
import demo.service.filter.DataTypes;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static demo.service.filter.DataTypes.DATE;
import static demo.service.filter.DataTypes.STRING;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
  private final UserFilterRepository userFilterRepository;
  private final FilterRepository filterRepository;
  private final ValidationService validationService;
  private final EntityManager entityManager;

  private static final FilterOptionsDto nameOption = new FilterOptionsDto()
          .setField("name")
          .setAllowedValue(STRING)
          .setCriteria(StringCriteria.getStringCriterias());

  private static final FilterOptionsDto dateOptions = new FilterOptionsDto()
          .setField("dob")
          .setAllowedValue(DATE)
          .setCriteria(DateCriteria.getDateCriterias());

  @Transactional
  public UserFilter saveFilters(UserFilterDto userFilter, User user) {
    // validate User filter
    var createdUserFilter = userFilterRepository.save(new UserFilter()
            .setName(userFilter.getName())
            .setUser(user));

    // step 2 save validate filters and save with parent ID
    for (FilterDto filter : userFilter.getFilters()) {
      validationService.validateEntity(filter, validationService.getFilterDtoValidator());
      // validate filter against dataMap
      var createdFilter = filterRepository.save(new Filter()
              .setCriteria(filter.getCriteria())
              .setUserFilterId(createdUserFilter.getId())
              .setValue(filter.getValue())
              .setFieldName(filter.getFieldName())
              .setType(filter.getType()));
      createdUserFilter.addFilter(createdFilter);
    }
    return createdUserFilter;
  }

  public List<FilterOptionsDto> findAllOptions() {
    List<FilterOptionsDto> response = new ArrayList<>();
    response.add(nameOption);
    response.add(dateOptions);

    return response;
  }

  public List<UserFilter> findByUser(User user) {
    return userFilterRepository.findAllByUser(user);
  }

  public UserFilter findById(Long userFilterId) {
    return userFilterRepository.getReferenceById(userFilterId);
  }

  public Predicate generatePredicateFromFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<User> itemRoot) {

    if (filter.getType() == DataTypes.STRING) {
      return stringFiltering(filter, criteriaBuilder, itemRoot);
    } else if (filter.getType() == DataTypes.DATE) {
      return dateFiltering(filter, criteriaBuilder, itemRoot);
    }
    else {
      // Number filtering
      return numberFiltering(filter, criteriaBuilder, itemRoot);
    }
  }


  private Predicate dateFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<User> itemRoot) {
    DateCriteria criteria = DateCriteria.valueOf(filter.getCriteria());

    return switch (criteria) {
      case BEFORE ->
              criteriaBuilder.lessThan(itemRoot.get(filter.getFieldName().name().toLowerCase()), Instant.parse(filter.getValue()));
      case AFTER ->
              criteriaBuilder.greaterThan(itemRoot.get(filter.getFieldName().name().toLowerCase()), Instant.parse(filter.getValue()));
      case EQUALS ->
              criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), Instant.parse(filter.getValue()));
      case BETWEEN ->
              criteriaBuilder.between(itemRoot.get(filter.getFieldName().name().toLowerCase()), Instant.parse(filter.getValue()), Instant.parse(filter.getValue()));
    };
  }

  private Predicate stringFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<User> itemRoot) {
    StringCriteria criteria = StringCriteria.valueOf(filter.getCriteria());
    return switch (criteria) {
      case CONTAINS ->
              criteriaBuilder.like(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
      case DOES_NOT_CONTAIN ->
              criteriaBuilder.notLike(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
      case EQUALS ->
              criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
    };
  }


  private Predicate numberFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<User> itemRoot) {
    NumberCriteria criteria = NumberCriteria.valueOf(filter.getCriteria());
    // TODO: implement number filtering
    return null;
  }

  public List<User> findAllByUserFilter(UserFilter existingFilter) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> itemRoot = criteriaQuery.from(User.class);

    List<Predicate> predicateList = existingFilter
            .getFilters()
            .stream()
            .map(filter -> generatePredicateFromFilter(filter, criteriaBuilder, itemRoot))
            .toList();

    Predicate finalPredicate = null;
    for (Predicate predicate : predicateList) {
      if (finalPredicate == null) {
        finalPredicate = predicate;
      } else {
        finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
      }
    }

    criteriaQuery.where(finalPredicate);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}
