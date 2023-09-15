package beer.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.BeerClient;
import shared.dto.beer.BeerCreationDto;

import java.util.List;

@Component
public record BeerRegisterListener(BeerClient beerClient) {

    @JmsListener(destination = "beer-creation-queue", containerFactory = "queueListenerFactory")
    public void createBeer(final List<BeerCreationDto> beerCreationDtoList) {
        beerClient.createBeer(beerCreationDtoList);
    }
}