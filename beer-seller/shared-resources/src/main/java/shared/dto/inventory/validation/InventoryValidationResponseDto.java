package shared.dto.inventory.validation;

import java.util.List;

public record InventoryValidationResponseDto(List<InventoryValidatedDto> validations) {
}
