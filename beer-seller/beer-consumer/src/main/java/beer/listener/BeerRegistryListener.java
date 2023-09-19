package beer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.BeerClient;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@Component
public record BeerRegistryListener(BeerClient beerClient, ObjectMapper objectMapper) {

    @JmsListener(destination = "beer-registry-queue", containerFactory = "queueListenerFactory")
    public void createBeer(final Message message) throws JMSException, JsonProcessingException {
        final List<BeerRegistryDto> beerRegistryDtoList = objectMapper.readValue(
            ((TextMessage) message).getText(),
            new TypeReference<>() {}
        );
        beerClient.createBeer(beerRegistryDtoList);
    }
}