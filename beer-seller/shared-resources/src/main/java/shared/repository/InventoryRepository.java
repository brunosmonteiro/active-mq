package shared.repository;

import shared.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByExternalId(final String externalId);
    List<Inventory> findByBeerIds(final Set<Long> beerIds);
}
