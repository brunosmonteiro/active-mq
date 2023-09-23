package relay.repository;

import java.util.Set;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relay.entity.beer.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    List<Beer> findByExternalIdIn(final Set<String> beerExternalIds);
}
