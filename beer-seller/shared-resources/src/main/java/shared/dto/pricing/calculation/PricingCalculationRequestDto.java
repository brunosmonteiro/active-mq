package shared.dto.pricing.calculation;

import java.util.List;

public class PricingCalculationRequestDto {
    private Long orderId;
    private List<PricingCalculationRequestBeerDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<PricingCalculationRequestBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<PricingCalculationRequestBeerDto> beers) {
        this.beers = beers;
    }
}
