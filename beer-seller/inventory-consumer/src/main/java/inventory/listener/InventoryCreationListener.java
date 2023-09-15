package inventory.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryCreationDto;

import java.util.List;

@Component
public record InventoryCreationListener(InventoryClient inventoryClient) {

    @JmsListener(destination = "inventory-creation-queue", containerFactory = "queueListenerFactory")
    public void receiveStockUpdate(final List<InventoryCreationDto> creationDtoList) {
        inventoryClient.createInventory(creationDtoList);
    }
}
