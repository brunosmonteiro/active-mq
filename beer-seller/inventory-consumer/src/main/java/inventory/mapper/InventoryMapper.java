package inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shared.dto.inventory.InventoryBeerConsumptionErrorDto;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.update.InventoryExternalUpdateDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(target = "actionType", expression = "java(InventoryActionType.STOCK_UPDATE))")
    InventoryErrorDto toInventoryUpdateErrorDto(
        final InventoryBeerDto inventoryBeerDto,
        final InventoryExternalUpdateDto inventoryExternalUpdateDto);

    @Mapping(target = "actionType", expression = "java(InventoryActionType.CONSUMPTION))")
    InventoryConsumptionErrorDto toInventoryConsumptionErrorDto(
            final InventoryBeerDto inventoryBeerDto,
            final OrderBeerResponseDto orderBeerResponseDto);

    InventoryBeerConsumptionErrorDto toInventoryBeerConsumptionErrorDto(final OrderResponseDto orderResponseDto);
}
