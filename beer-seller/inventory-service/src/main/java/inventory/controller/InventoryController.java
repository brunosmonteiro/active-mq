package inventory.controller;

import inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.mapper.InventoryMapper;

import java.util.Set;

@RestController
@RequestMapping("/inventory-service")
public record InventoryController(InventoryService inventoryService, InventoryMapper inventoryMapper) {
    @GetMapping("/beers")
    public InventoryResponseDto getInventory(@RequestParam final Set<Long> beerIds) {
        return inventoryService.getInventory(beerIds);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody final InventoryCreationDto inventoryDto) {
        inventoryService.createInventory(inventoryDto);
    }
}
