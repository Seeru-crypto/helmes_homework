package demo.service;

import demo.exception.BusinessException;
import demo.model.Sector;
import demo.model.User;
import demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static demo.exception.BusinessException.USER_NAME_EXISTS;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SectorService sectorService;

    private static final String USER_ID = "id";
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_SIZE_OF_PAGE = 10;

    protected void validateUserData(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new BusinessException(USER_NAME_EXISTS) {
            };
        }
    }

    @Transactional
    public User save(User user, List<String> sectorsNames) {
        validateUserData(user);
        List<Sector> sectors = sectorsNames.stream().map(sectorService::getSectorByName).toList();
        user.setSectors(sectors);
        return userRepository.save(user);
    }

    public Page<User> findAll(String sortBy, Integer pageNumber, Integer sizeOfPage) {

        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (sizeOfPage == null) {
            sizeOfPage = DEFAULT_SIZE_OF_PAGE;
        }
        if (sortBy == null || sortBy.equals("")) {
            sortBy = USER_ID;
        }
        Pageable pageable = PageRequest.of(pageNumber, sizeOfPage, Sort.by(Sort.Direction.ASC, sortBy));
        return userRepository.findAll(pageable);
    }
}
