package demo.service;

import demo.exception.NotFoundException;
import demo.model.Filter;
import demo.model.Sector;
import demo.model.UserFilter;
import demo.repository.FilterRepository;
import demo.repository.SectorRepository;
import demo.service.filter.DataTypes;
import demo.service.filter.Filters;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static demo.service.filter.DataTypes.DATE;
import static demo.service.filter.DataTypes.NUMBER;
import static demo.service.filter.DataTypes.STRING;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
  private final FilterRepository filterRepository;
  private final ValidationService validationService;

  @Transactional
  public void saveFilters(UserFilter userFilter) {
    var dataMap = DataTypes.getDataMap();
    // step 1 save parent obj & get ID

    // step 2 save validate filters and save with parent ID
    userFilter.getFilters().forEach(filter -> {
      validationService.validate(filter);
      filter.setUser(userFilter);
      filterRepository.save(filter);
    });

  }
}
