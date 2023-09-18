package destination.controller;

import destination.producer.BeerRegistryProducer;
import destination.producer.InventoryRegistryProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.beer.BeerRegistryDto;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public record InventoryController(BeerRegistryProducer beerRegistryProducer, InventoryRegistryProducer inventoryCreationProducer) {

    @PostMapping("/external")
    public void publishInvent() {
//        final var external = List.of(
//            new InventoryUpdateDto("BEER_001", 10),
//            new InventoryUpdateDto("BEER_002", 20),
//            new InventoryUpdateDto("BEER_003", 30),
//            new InventoryUpdateDto("BEER_004", 40),
//            new InventoryUpdateDto("BEER_005", 50));
//        inventoryCreationProducer.sendMessage(external);
        final var registry = List.of(
                new BeerRegistryDto("BEER_001", "Mariana Monteiro", "Cerveja da Mariana Monteiro"),
                new BeerRegistryDto("BEER_002", "Lily Wiggle", "Cerveja de Le Picles"),
                new BeerRegistryDto("BEER_003", "Nina Pompona", "Cerveja do Pão (Anau)"),
                new BeerRegistryDto("BEER_004", "Mumu", "Cerveja de Mu"),
                new BeerRegistryDto("BEER_005", "El Paton", "Cerveja do Pato"));
        beerRegistryProducer.sendMessage(registry);
    }
}
