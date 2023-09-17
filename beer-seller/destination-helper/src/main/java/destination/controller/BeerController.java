package destination.controller;

import destination.producer.BeerRegistryProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@RestController
@RequestMapping("/beers")
public record BeerController(BeerRegistryProducer beerRegistryProducer) {
    @PostMapping("/external")
    public void publishEvent() {
        final var registry = List.of(
            new BeerRegistryDto("BEER_001", "Mariana Monteiro", "Cerveja da Mariana Monteiro"),
            new BeerRegistryDto("BEER_002", "Lili Wiggle", "Cerveja de Le Picles"),
            new BeerRegistryDto("BEER_003", "Nina Pompona", "Cerveja Nina (Anau)"),
            new BeerRegistryDto("BEER_004", "Mumu", "Cerveja de Mu"),
            new BeerRegistryDto("BEER_005", "El Paton", "Cerveja do Pato"));
        beerRegistryProducer.sendMessage(registry);
    }
}
