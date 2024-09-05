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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilteringLogicService {
    private final EntityManager entityManager;

    public <T> List<T> findAllByFilter(UserFilter userFilter, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> itemRoot = criteriaQuery.from(entityClass);

        Predicate predicate = userFilter.getFilters()
                .stream()
                .map(filter -> generatePredicateFromFilter(filter, criteriaBuilder, itemRoot))
                .filter(Objects::nonNull)  // Filter out null values
                .reduce(criteriaBuilder::and)
                .orElse(null);

        if (predicate == null) {
            log.info("predicate is null, with userFilter: {}", userFilter);
        }

        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private <T> Predicate generatePredicateFromFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
        log.info("generating predicate from: {}", filter);
        return switch (filter.getType()) {
            case STRING -> stringFiltering(filter, criteriaBuilder, itemRoot);
            case DATE -> dateFiltering(filter, criteriaBuilder, itemRoot);
            case NUMBER -> numberFiltering(filter, criteriaBuilder, itemRoot);
        };
    }

    private <T> Predicate dateFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
        DateCriteria criteria = DateCriteria.valueOf(filter.getCriteriaValue());
        Expression<LocalDate> dateField = itemRoot.get(filter.getFieldName().name().toLowerCase());
        LocalDate filterDate = Instant.parse(filter.getValue()).atZone(ZoneOffset.UTC).toLocalDate();

        return switch (criteria) {
            case BEFORE -> criteriaBuilder.lessThanOrEqualTo(dateField, filterDate);
            case AFTER -> criteriaBuilder.greaterThan(dateField, filterDate);
            case EQUALS -> {
                LocalDate nextDay = filterDate.plusDays(1);
                yield criteriaBuilder.and(
                        criteriaBuilder.greaterThan(dateField, filterDate),
                        criteriaBuilder.lessThanOrEqualTo(dateField, nextDay));
            }
        };
    }

    private <T> Predicate stringFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
        StringCriteria criteria = StringCriteria.valueOf(filter.getCriteriaValue());
        return switch (criteria) {
            case CONTAINS ->
                    criteriaBuilder.like(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
            case DOES_NOT_CONTAIN ->
                    criteriaBuilder.notLike(itemRoot.get(filter.getFieldName().name().toLowerCase()), "%" + filter.getValue() + "%");
            case EQUALS ->
                    criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
        };
    }

    private <T> Predicate numberFiltering(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> itemRoot) {
        NumberCriteria criteria = NumberCriteria.valueOf(filter.getCriteriaValue());
        return switch (criteria) {
            case SMALLER_THAN ->
                    criteriaBuilder.lessThanOrEqualTo(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
            case EQUALS ->
                    criteriaBuilder.equal(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
            case BIGGER_THAN ->
                    criteriaBuilder.greaterThan(itemRoot.get(filter.getFieldName().name().toLowerCase()), filter.getValue());
        };
    }
}
