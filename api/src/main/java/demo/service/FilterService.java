package demo.service;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.FilterRepository;
import demo.repository.UserFilterRepository;
import demo.service.filter.DataTypes;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
  private final UserFilterRepository userFilterRepository;
  private final FilterRepository filterRepository;
  private final ValidationService validationService;

  @Transactional
  public UserFilter saveFilters(UserFilterDto userFilter, User user) {
    var dataMap = DataTypes.getDataMap();

    // validate User filter
    var createdUserFilter = userFilterRepository.save(new UserFilter()
            .setName(userFilter.getName())
            .setUser(user));

    // step 2 save validate filters and save with parent ID
    for (FilterDto filter : userFilter.getFilters()) {
      // validate filter against dataMap
      var createdFilter = filterRepository.save(new Filter()
              .setCriteria(filter.getCriteria())
              .setUserFilterId(createdUserFilter.getId())
              .setValue(filter.getValue())
              .setType(filter.getType()));
      createdUserFilter.addFilter(createdFilter);
    }
    return createdUserFilter;
  }
}
