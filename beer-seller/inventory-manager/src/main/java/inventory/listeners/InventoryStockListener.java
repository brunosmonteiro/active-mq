package inventory.listeners;

import dto.inventory.InventoryStockUpdateDto;
import entity.inventory.Inventory;
import inventory.database.InventoryRepository;
import mappers.inventory.InventoryMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public record InventoryStockListener(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {

    // This method will be triggered when a message is sent to the 'inventory-stock-queue' destination
    @JmsListener(destination = "inventory-stock-queue")
    public void receiveOrder(final InventoryStockUpdateDto update) {
        final Inventory inventory = inventoryRepository.findByExternalId(update.getBeerId());
        if (inventory == null) {
            final Inventory newEntry = inventoryMapper.toInventory(update);
            inventoryRepository.save(newEntry);
        } else {
            inventory.setQuantity(inventory.getQuantity() + update.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
