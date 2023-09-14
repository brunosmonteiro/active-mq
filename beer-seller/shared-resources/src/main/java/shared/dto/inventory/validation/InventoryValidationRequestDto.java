package shared.dto.inventory.validation;

import java.util.List;

public class InventoryValidationRequestDto {
    private List<InventoryValidationDto> validations;

    public List<InventoryValidationDto> getValidations() {
        return validations;
    }

    public void setValidations(final List<InventoryValidationDto> validations) {
        this.validations = validations;
    }
}
