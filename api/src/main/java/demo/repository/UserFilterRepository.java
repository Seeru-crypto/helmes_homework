package demo.repository;

import demo.model.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserFilterRepository extends JpaRepository<UserFilter, Long> {

    @Query("""
            SELECT u
            FROM UserFilter u
            WHERE  u.user.id=:userId
                                    """)
    List<UserFilter> findAllByUser(@Param("userId") UUID userId);
}
