package shared.dto.pricing;

import shared.constants.pricing.PricingStatus;

import java.math.BigDecimal;
import java.util.List;

public class PricingResponseDto {
    private Long orderId;
    private List<PricingResponseBeerDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<PricingResponseBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<PricingResponseBeerDto> beers) {
        this.beers = beers;
    }
}
