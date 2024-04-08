package demo.service;

import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;
    private final ValidationService validationService;



}
