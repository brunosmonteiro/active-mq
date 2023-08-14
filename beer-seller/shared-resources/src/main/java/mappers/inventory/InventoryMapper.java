package mappers.inventory;

import dto.inventory.InventoryStockUpdateDto;
import entity.inventory.Inventory;

public interface InventoryMapper {
    Inventory toInventory(final InventoryStockUpdateDto update);
}
