package demo.repository;

import demo.controller.dto.FilterOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterOptionsRepository extends JpaRepository<FilterOptions, Long> {
}
