package demo.service.filter;

import demo.model.Filter;
import demo.model.UserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilteringLogicService {
  private final EntityManager entityManager;

  public <T> List<T> findAllByFilter(UserFilter existingFilter, Class<T> entityClass) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
    Root<T> itemRoot = criteriaQuery.from(entityClass);

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

  private <T> Predicate generatePredicateFromFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
    return switch (filter.getType()) {
      case STRING -> stringFiltering(filter, criteriaBuilder, itemRoot);
      case DATE -> dateFiltering(filter, criteriaBuilder, itemRoot);
      case NUMBER ->  numberFiltering(filter, criteriaBuilder, itemRoot);
    };
  }

  private <T> Predicate dateFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
    DateCriteria criteria = DateCriteria.valueOf(filter.getCriteria());
    Expression<LocalDate> dateField = itemRoot.get(filter.getFieldName().name().toLowerCase());
    LocalDate filterDate = Instant.parse(filter.getValue()).atZone(ZoneOffset.UTC).toLocalDate();

    return switch (criteria) {
      case BEFORE ->
              criteriaBuilder.lessThan(dateField, filterDate);
      case AFTER ->
              criteriaBuilder.greaterThan(dateField, filterDate);
      case EQUALS -> {
        LocalDate nextDay = filterDate.plusDays(1);
        yield criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(dateField, filterDate),
                criteriaBuilder.lessThan(dateField, nextDay));
      }
      case BETWEEN ->
              // TODO: implement BETWEEN logic, for that filter value has to be array or a new value must be added
              null;
    };
  }

  private <T> Predicate stringFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
    StringCriteria criteria = StringCriteria.valueOf(filter.getCriteria());
    return switch (criteria) {
      case CONTAINS ->
              criteriaBuilder.like(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
      case DOES_NOT_CONTAIN ->
              criteriaBuilder.notLike(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
      case EQUALS -> criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
    };
  }

  private <T> Predicate numberFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
    NumberCriteria criteria = NumberCriteria.valueOf(filter.getCriteria());
    // TODO: implement number filtering
    return null;
  }
}
