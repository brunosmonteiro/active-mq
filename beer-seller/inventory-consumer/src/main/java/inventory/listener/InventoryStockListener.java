package inventory.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.InventoryUpdateDto;

@Component
public record InventoryStockListener(InventoryService inventoryService) {

    @JmsListener(destination = "inventory-stock-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final InventoryUpdateDto update) {
        inventoryService.updateStock(update);
    }
}
