package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.List;
import java.util.Set;

@FeignClient(value = "InventoryClient", url = "${url.inventory}")
public interface InventoryClient {
    @GetMapping
    List<InventoryBeerDto> getInventoryBeers(
        @RequestParam final Set<Long> beerIds,
        @RequestParam final Set<String> beerExternalIds);

    default List<InventoryBeerDto> getInventoryBeersById(@RequestParam final Set<Long> beerIds) {
        return getInventoryBeers(beerIds, null);
    }

    default List<InventoryBeerDto> getInventoryBeersByExternalId(
            @RequestParam final Set<String> beerExternalIds) {
        return getInventoryBeers(null, beerExternalIds);
    }

    @PostMapping
    void createInventory(@RequestBody final List<InventoryCreationDto> inventoryDtoList);

    @PutMapping
    void updateInventories(@RequestBody final List<InventoryUpdateDto> inventoryUpdateDto);
}
