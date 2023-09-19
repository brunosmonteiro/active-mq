package destination.controller;

import destination.producer.BeerRegistryProducer;
import destination.producer.InventoryRegistryProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.update.InventoryUpsertDto;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public record InventoryController(BeerRegistryProducer beerRegistryProducer, InventoryRegistryProducer inventoryCreationProducer) {

    @PostMapping
    public void publishInvent() {
        final var external = List.of(
            new InventoryUpsertDto("BEER_001", 10),
            new InventoryUpsertDto("BEER_002", 20),
            new InventoryUpsertDto("BEER_003", 30),
            new InventoryUpsertDto("BEER_004", 40),
            new InventoryUpsertDto("BEER_005", 50));
        inventoryCreationProducer.sendMessage(external);
    }
}
