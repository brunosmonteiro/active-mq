package shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shared.entity.beer.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
}
