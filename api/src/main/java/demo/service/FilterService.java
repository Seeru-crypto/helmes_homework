package demo.service;

import demo.controller.dto.FilterDto;
import demo.controller.dto.FilterOptions;
import demo.controller.dto.UserFilterDto;
import demo.mapper.FilterMapper;
import demo.mapper.UserFilterMapper;
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
  private final UserFilterMapper userFilterMapper;
  private final FilterMapper filterMapper;

  @Transactional
  public UserFilter saveFilters(UserFilterDto userFilterDto, UUID userId) {
    User user = userService.findById(userId);

    validationService.validateEntity(userFilterDto, validationService.getUserFilterDtoValidator());
    UserFilter createdUserFilter = userFilterRepository.save(userFilterMapper.toEntity(userFilterDto, user));

    userFilterDto.getFilters().stream()
            .map(this::validateFilterDto)
            .map(filterMapper::toEntity)
            .forEach(filter -> saveAndUpdateUserFilter(filter, createdUserFilter));

    return createdUserFilter;
  }

  private FilterDto validateFilterDto(FilterDto filterDto) {
    validationService.validateEntity(filterDto, validationService.getFilterDtoValidator());
    return filterDto;
  }

  private void saveAndUpdateUserFilter(Filter filter, UserFilter createdUserFilter) {
    filterRepository.save(filter.setUserFilterId(createdUserFilter.getId()));
    createdUserFilter.addFilter(filter);
  }

  public List<FilterOptions> findAllOptions() {
    return filterOptionsRepository.findAll();
  }

  public List<UserFilter> findByUser(UUID userId) {
    return userFilterRepository.findAllByUser(userId);
  }

  public UserFilter findById(Long userFilterId) {
    return userFilterRepository.getReferenceById(userFilterId);
  }
}
