package inventory.mapper;

import org.mapstruct.Mapper;
import shared.dto.beer.BeerDto;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.inventory.update.InventoryUpsertDto;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryErrorDto toInventoryUpdateErrorDto(
        final InventoryBeerDto inventoryBeerDto,
        final InventoryUpsertDto inventoryUpdateDto);

    InventoryErrorDto toInventoryUpdateErrorDto(
        final BeerDto beerDto,
        final InventoryUpsertDto inventoryUpdateDto);
}
