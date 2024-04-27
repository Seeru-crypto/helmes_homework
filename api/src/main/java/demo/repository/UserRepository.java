package demo.repository;

import demo.model.Sector;
import demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByName (String name);

    List<User> findAllBySectorsContains(Sector sector);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
