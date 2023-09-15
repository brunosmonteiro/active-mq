package shared.dto.inventory.validation;

import shared.dto.order.orchestration.OrderBeerOrchestrationPart;
import shared.dto.order.orchestration.OrderOrchestrationPartDto;

import java.util.List;

public class InventoryValidationResponseDto extends OrderOrchestrationPartDto {
    public InventoryValidationResponseDto(
            final Long orderId,
            final String consumerId,
            final List< ? extends OrderBeerOrchestrationPart> beers) {
        super(orderId, consumerId, beers);
    }
}
