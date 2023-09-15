package shared.dto.pricing.calculation;

import shared.constants.pricing.PricingStatus;
import shared.dto.order.orchestration.OrderBeerOrchestrationPart;

import java.math.BigDecimal;

public class PricingCalculationResponseBeerDto extends OrderBeerOrchestrationPart {
    private PricingStatus status;
    private BigDecimal unitPrice;

    public PricingCalculationResponseBeerDto(
            final Long beerId,
            final PricingStatus status,
            final BigDecimal unitPrice) {
        super(beerId);
        this.status = status;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public PricingStatus getStatus() {
        return status;
    }

    public void setStatus(final PricingStatus status) {
        this.status = status;
    }
}
