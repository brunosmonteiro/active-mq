package shared.dto.pricing.calculation;

import shared.dto.order.orchestration.OrderBeerOrchestrationPart;
import shared.dto.order.orchestration.OrderOrchestrationPartDto;

import java.math.BigDecimal;
import java.util.List;

public class PricingCalculationResponseDto extends OrderOrchestrationPartDto {
    private BigDecimal totalPrice;

    public PricingCalculationResponseDto(
            final String orderAggregationId,
            final String consumerId,
            final List<? extends OrderBeerOrchestrationPart> beers,
            final BigDecimal totalPrice) {
        super(orderAggregationId, consumerId, beers);
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
