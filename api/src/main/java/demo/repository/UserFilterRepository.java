package demo.repository;

import demo.model.User;
import demo.model.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFilterRepository extends JpaRepository<UserFilter, Long> {

  List<UserFilter> findAllByUser(User user);


}
