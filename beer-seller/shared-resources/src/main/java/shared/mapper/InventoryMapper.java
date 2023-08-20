package shared.mapper;

import org.mapstruct.Mapper;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.entity.inventory.Inventory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toInventory(final InventoryCreationDto inventoryDto);

    InventoryBeerDto toInventoryBeerDto(final Inventory inventory);

    default InventoryResponseDto toInventoryResponseDto(final List<Inventory> inventories) {
        return new InventoryResponseDto(inventories.stream().map(this::toInventoryBeerDto).toList());
    }
}
