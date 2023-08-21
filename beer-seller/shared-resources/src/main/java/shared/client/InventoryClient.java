package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.dto.inventory.InventoryResponseDto;

import java.util.Set;

@FeignClient(value = "InventoryClient", url = "${url.inventory}")
public interface InventoryClient {
    @GetMapping("/inventories")
    InventoryResponseDto getInventory(@RequestParam final Set<Long> beerIds);
}
