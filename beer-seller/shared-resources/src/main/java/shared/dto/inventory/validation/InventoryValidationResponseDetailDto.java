package shared.dto.inventory.validation;

import shared.constants.inventory.InventoryValidationStatus;
import shared.dto.order.orchestration.OrderBeerOrchestrationPart;

public class InventoryValidationResponseDetailDto extends OrderBeerOrchestrationPart {
    private InventoryValidationStatus status;

    public InventoryValidationResponseDetailDto(final Long beerId) {
        super(beerId);
    }

    public InventoryValidationStatus getStatus() {
        return status;
    }

    public void setStatus(final InventoryValidationStatus status) {
        this.status = status;
    }
}
