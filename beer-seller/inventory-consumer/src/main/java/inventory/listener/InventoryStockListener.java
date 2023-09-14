package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.update.InventoryExternalUpdateDto;

import java.util.List;

@Component
public record InventoryStockListener(InventoryService inventoryService) {

    @JmsListener(destination = "inventory-stock-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final List<InventoryExternalUpdateDto> update) {
        inventoryService.updateStock(update);
    }
}
