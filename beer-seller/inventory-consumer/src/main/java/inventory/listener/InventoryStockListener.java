package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.beer.InventoryUpdateDto;

@Component
public record InventoryStockListener(InventoryService inventoryService) {
    @JmsListener(destination = "inventory-stock-queue", containerFactory = "topicListenerFactory")
    public void receiveStockUpdate(final InventoryUpdateDto update) {
        inventoryService.updateStock(update);
    }
}
