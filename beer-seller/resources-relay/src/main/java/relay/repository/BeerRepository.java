package relay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relay.entity.beer.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
}
