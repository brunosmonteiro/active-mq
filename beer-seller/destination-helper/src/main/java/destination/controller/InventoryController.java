package destination.controller;

import destination.producer.InventoryRegistryProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public record InventoryController(InventoryRegistryProducer inventoryCreationProducer) {

    @PostMapping("/external")
    public void publishInvent() {
        final var external = List.of(
            new InventoryUpdateDto("BEER_001", 10),
            new InventoryUpdateDto("BEER_002", 20),
            new InventoryUpdateDto("BEER_003", 30),
            new InventoryUpdateDto("BEER_004", 40),
            new InventoryUpdateDto("BEER_005", 50));
        inventoryCreationProducer.sendMessage(external);
    }
}
