package shared.dto.inventory.validation;

import java.util.List;

public record InventoryValidationResponseDto(Long orderId, List<InventoryValidationResponseDetailDto> beers) {

}
