package inventory.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.InventoryUpdateDto;

@Component
public record InventoryStockListener(InventoryService inventoryService, ObjectMapper objectMapper) {
    @JmsListener(destination = "inventory-stock-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final String update) {
        try {
            inventoryService.updateStock(objectMapper.readValue(update, InventoryUpdateDto.class));
        } catch (final JsonProcessingException e) {
            System.out.println("Error reading JSON");
        }
    }
}
