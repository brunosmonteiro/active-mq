package shared.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shared.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByBeerExternalId(final String externalId);

    @Query("SELECT i FROM Inventory i WHERE i.beer.id IN :beerIds")
    List<Inventory> findByBeerIds(final Set<Long> beerIds);
}
