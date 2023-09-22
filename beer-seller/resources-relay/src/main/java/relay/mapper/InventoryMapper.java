package relay.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import relay.entity.inventory.Inventory;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    List<Inventory> toInventoryList(final List<InventoryCreationDto> inventoryDtoList);

    InventoryBeerDto toInventoryBeerDto(final Inventory inventory);

    List<InventoryBeerDto> toInventoryBeerDtoList(final List<Inventory> inventories);
}
