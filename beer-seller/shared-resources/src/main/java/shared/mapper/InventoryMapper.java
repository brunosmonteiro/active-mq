package shared.mapper;

import org.mapstruct.Mapper;
import shared.dto.beer.InventoryBeerDto;
import shared.dto.beer.InventoryResponseDto;
import shared.dto.beer.InventoryUpdateDto;
import shared.entity.inventory.Inventory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toInventory(final InventoryUpdateDto update);
    InventoryBeerDto toInventoryBeerDto(final Inventory inventory);
    default InventoryResponseDto toInventoryResponseDto(final List<Inventory> inventories) {
        return new InventoryResponseDto(inventories.stream().map(this::toInventoryBeerDto).toList());
    }
}
