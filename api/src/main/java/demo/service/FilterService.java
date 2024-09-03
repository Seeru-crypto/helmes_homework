package demo.service;

import demo.controller.dto.FilterDto;
import demo.controller.dto.FilterOptions;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.FilterOptionsRepository;
import demo.repository.FilterRepository;
import demo.repository.UserFilterRepository;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
  private final UserFilterRepository userFilterRepository;
  private final FilterRepository filterRepository;
  private final FilterOptionsRepository filterOptionsRepository;
  private final ValidationService validationService;
  private final UserService userService;

  @Transactional
  public UserFilter saveFilters(UserFilterDto userFilter, UUID userId) {
    User user = userService.findById(userId);

    validationService.validateEntity(userFilter, validationService.getUserFilterDtoValidator());
    var createdUserFilter = userFilterRepository.save(new UserFilter()
            .setName(userFilter.getName())
            .setUser(user));

    for (FilterDto filter : userFilter.getFilters()) {
      validationService.validateEntity(filter, validationService.getFilterDtoValidator());
      // validate filter against dataMap
      var createdFilter = filterRepository.save(new Filter()
              .setCriteriaValue(filter.getCriteriaValue())
              .setUserFilterId(createdUserFilter.getId())
              .setValue(filter.getValue())
              .setFieldName(filter.getFieldName())
              .setType(filter.getType()));
      createdUserFilter.addFilter(createdFilter);
    }
    return createdUserFilter;
  }

  public List<FilterOptions> findAllOptions() {
    return filterOptionsRepository.findAll();
  }

  public List<UserFilter> findByUser(UUID userId) {
    User user = userService.findById(userId);
    return userFilterRepository.findAllByUser(user);
  }

  public UserFilter findById(Long userFilterId) {
    return userFilterRepository.getReferenceById(userFilterId);
  }
}
