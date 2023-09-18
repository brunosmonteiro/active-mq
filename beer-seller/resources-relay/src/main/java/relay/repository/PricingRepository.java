package relay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import relay.entity.pricing.Pricing;

import java.util.List;
import java.util.Set;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {
    @Query("SELECT i FROM Pricing i WHERE i.beer.id IN :beerIds")
    List<Pricing> findByBeerIds(final Set<Long> beerIds);
}
