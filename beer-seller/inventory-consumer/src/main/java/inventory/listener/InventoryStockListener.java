package inventory.listener;

import inventory.service.InventoryUpdateService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.beer.BeerRegistryDto;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Component
public record InventoryStockListener(InventoryUpdateService inventoryUpdateService) {

    @JmsListener(destination = "inventory-stock-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final List<InventoryUpdateDto> update) {
        inventoryUpdateService.updateStock(update);
    }
}
