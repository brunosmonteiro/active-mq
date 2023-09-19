package inventory.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.service.InventoryUpdateService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.beer.BeerRegistryDto;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Component
public record InventoryStockListener(
        InventoryUpdateService inventoryUpdateService,
        ObjectMapper objectMapper) {

    @JmsListener(destination = "inventory-stock-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final Message message) throws JMSException, JsonProcessingException {
        final List<InventoryUpdateDto> update = objectMapper.readValue(
            ((TextMessage) message).getText(),
            new TypeReference<>() {}
        );
        inventoryUpdateService.updateStock(update);
    }
}
