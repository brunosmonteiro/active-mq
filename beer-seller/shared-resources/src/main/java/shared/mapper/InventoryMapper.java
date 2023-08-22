package shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.entity.inventory.Inventory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toInventory(final InventoryCreationDto inventoryDto);

    @Mapping(target = "price", source = "beer.price")
    InventoryBeerDto toInventoryBeerDto(final Inventory inventory);

    default InventoryResponseDto toInventoryResponseDto(final List<Inventory> inventories) {
        return new InventoryResponseDto(inventories.stream().map(this::toInventoryBeerDto).toList());
    }
}
