package demo.service;

import demo.controller.dto.FilterDto;
import demo.controller.dto.FilterOptionsDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.User;
import demo.model.UserFilter;
import demo.repository.FilterRepository;
import demo.repository.UserFilterRepository;
import demo.service.filter.DateCriteria;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static demo.service.filter.DataTypes.DATE;
import static demo.service.filter.DataTypes.STRING;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
  private final UserFilterRepository userFilterRepository;
  private final FilterRepository filterRepository;
  private final ValidationService validationService;
  private final UserService userService;

  private static final FilterOptionsDto nameOption = new FilterOptionsDto()
          .setField("name")
          .setAllowedValue(STRING)
          .setCriteria(StringCriteria.getStringCriterias());

  private static final FilterOptionsDto dateOptions = new FilterOptionsDto()
          .setField("dob")
          .setAllowedValue(DATE)
          .setCriteria(DateCriteria.getDateCriterias());

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

  public List<UserFilter> findByUser(UUID userId) {
    User user = userService.findById(userId);
    return userFilterRepository.findAllByUser(user);
  }

  public UserFilter findById(Long userFilterId) {
    return userFilterRepository.getReferenceById(userFilterId);
  }
}
