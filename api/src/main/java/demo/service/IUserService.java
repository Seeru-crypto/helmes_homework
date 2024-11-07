package demo.service;

import demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User save(User user);

    void delete(UUID id);

    User update(User entity, UUID userId);

    Page<User> findAll(Pageable pageable);

    User findById(UUID id);

    List<User> findAllBySector(Long sectorId);

    void removeSectorFromAllUsers(Long sectorId);
}
