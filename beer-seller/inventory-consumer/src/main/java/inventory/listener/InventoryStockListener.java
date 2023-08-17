package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.InventoryUpdateDto;

@Component
public record InventoryStockListener(InventoryService inventoryService) {

    // This method will be triggered when a message is sent to the 'inventory-stock-queue' destination
    @JmsListener(destination = "inventory-stock-queue", containerFactory = "topicListenerFactory")
    public void receiveStockUpdate(final InventoryUpdateDto update) {
        inventoryService.updateStock(update);
    }
}
