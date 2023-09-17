package beer.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.BeerClient;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@Component
public record BeerRegistryListener(BeerClient beerClient) {

    @JmsListener(destination = "beer-registry-queue", containerFactory = "queueListenerFactory")
    public void createBeer(final List<BeerRegistryDto> beerRegistryDtoList) {
        beerClient.createBeer(beerRegistryDtoList);
    }
}