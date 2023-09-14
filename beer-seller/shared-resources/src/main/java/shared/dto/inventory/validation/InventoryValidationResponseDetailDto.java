package shared.dto.inventory.validation;

import shared.constants.inventory.InventoryValidationStatus;

public class InventoryValidationResponseDetailDto {
    private Long beerId;
    private InventoryValidationStatus status;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }

    public InventoryValidationStatus getStatus() {
        return status;
    }

    public void setStatus(final InventoryValidationStatus status) {
        this.status = status;
    }
}
