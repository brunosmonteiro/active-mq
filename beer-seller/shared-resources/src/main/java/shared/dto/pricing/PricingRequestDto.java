package shared.dto.pricing;

import java.util.List;

public class PricingRequestDto {
    private Long orderId;
    private List<PricingRequestBeerDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<PricingRequestBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<PricingRequestBeerDto> beers) {
        this.beers = beers;
    }
}
