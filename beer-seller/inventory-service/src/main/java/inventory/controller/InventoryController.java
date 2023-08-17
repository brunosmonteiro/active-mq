package inventory.controller;

import inventory.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.InventoryResponseDto;
import shared.mapper.InventoryMapper;

import java.util.Set;

@RestController
public record InventoryController(InventoryService inventoryService, InventoryMapper inventoryMapper) {
    @GetMapping("/beers")
    public InventoryResponseDto getInventory(@RequestParam final Set<Long> beerIds) {
        return inventoryService.getInventory(beerIds);
    }
}
