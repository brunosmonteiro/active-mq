package inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryUpdateDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.order.OrderBeerResponseDto;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryErrorDto toInventoryUpdateErrorDto(
        final InventoryBeerDto inventoryBeerDto,
        final InventoryUpdateDto inventoryUpdateDto);
}
