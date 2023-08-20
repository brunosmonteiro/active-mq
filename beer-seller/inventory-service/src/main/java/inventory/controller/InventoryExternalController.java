package inventory.controller;

import inventory.service.InventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.InventoryUpdateDto;

@RestController
@RequestMapping("/inventory-external")
public record InventoryExternalController(InventoryService inventoryService) {

    @PostMapping
    public void publishEvent(@RequestBody final InventoryUpdateDto updateDto) {
        inventoryService.publishEvent(updateDto);
    }
}
