package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@Component
public record BeerRegistryProducer(JmsTemplate jmsTemplate) {
    private static final String BEER_REGISTRY_QUEUE = "beer-registry-queue";

    public void sendMessage(final List<BeerRegistryDto> registry) {
        jmsTemplate.convertAndSend(BEER_REGISTRY_QUEUE, registry);
    }
}
