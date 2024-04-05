package demo.repository;

import demo.model.Sector;
import demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName (String name);

    List<User> findAllBySectorsContains(Sector sector);

}
