package relay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import relay.entity.inventory.Inventory;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toInventory(final InventoryCreationDto inventoryDto);

    @Mapping(target = "price", source = "beer.price")
    InventoryBeerDto toInventoryBeerDto(final Inventory inventory);

    List<InventoryBeerDto> toInventoryBeerDtoList(final List<Inventory> inventories);
}
