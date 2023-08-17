package shared.mapper;

import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.entity.inventory.Inventory;

import java.util.List;

public interface InventoryMapper {
    Inventory toInventory(final InventoryUpdateDto update);
    InventoryResponseDto toInventoryResponseDto(final List<Inventory> inventories);
}
