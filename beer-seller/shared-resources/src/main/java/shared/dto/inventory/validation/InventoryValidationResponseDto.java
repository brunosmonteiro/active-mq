package shared.dto.inventory.validation;

import shared.dto.order.orchestration.OrderBeerOrchestrationPart;
import shared.dto.order.orchestration.OrderOrchestrationPartDto;

import java.util.List;

public class InventoryValidationResponseDto extends OrderOrchestrationPartDto {
    public InventoryValidationResponseDto(
            final String orderAggregationId,
            final String consumerId,
            final List< ? extends OrderBeerOrchestrationPart> beers) {
        super(orderAggregationId, consumerId, beers);
    }
}
