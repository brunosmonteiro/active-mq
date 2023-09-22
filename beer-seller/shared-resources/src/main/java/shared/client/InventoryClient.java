package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.update.InventoryUpsertDto;

import java.util.List;
import java.util.Set;

@FeignClient(value = "InventoryClient", url = "${url.resources-relay}/inventories")
public interface InventoryClient {
    @GetMapping
    List<InventoryBeerDto> getInventoryBeers(
        @RequestParam(required = false) final Set<Long> beerIds,
        @RequestParam(required = false) final Set<String> beerExternalIds);

    default List<InventoryBeerDto> getInventoryBeersById(final Set<Long> beerIds) {
        return getInventoryBeers(beerIds, null);
    }

    default List<InventoryBeerDto> getInventoryBeersByExternalId(final Set<String> beerExternalIds) {
        return getInventoryBeers(null, beerExternalIds);
    }

    @PostMapping
    void upsertInventories(@RequestBody final List<InventoryUpsertDto> inventoryUpdateDto);
}
