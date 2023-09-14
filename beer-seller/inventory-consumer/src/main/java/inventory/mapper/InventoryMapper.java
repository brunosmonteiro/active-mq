package inventory.mapper;

import org.mapstruct.Mapper;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.inventory.update.InventoryUpdateDto;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryErrorDto toInventoryUpdateErrorDto(
        final InventoryBeerDto inventoryBeerDto,
        final InventoryUpdateDto inventoryUpdateDto);
}
