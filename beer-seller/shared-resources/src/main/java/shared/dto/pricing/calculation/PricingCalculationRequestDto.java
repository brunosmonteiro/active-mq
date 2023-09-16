package shared.dto.pricing.calculation;

import java.util.List;

public class PricingCalculationRequestDto {
    private String orderAggregationId;
    private String consumerId;
    private List<PricingCalculationRequestBeerDto> beers;

    public PricingCalculationRequestDto() {
    }

    public PricingCalculationRequestDto(
            final String orderAggregationId,
            final String consumerId,
            final List<PricingCalculationRequestBeerDto> beers) {
        this.orderAggregationId = orderAggregationId;
        this.consumerId = consumerId;
        this.beers = beers;
    }

    public String getOrderAggregationId() {
        return orderAggregationId;
    }

    public void setOrderAggregationId(String orderAggregationId) {
        this.orderAggregationId = orderAggregationId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public List<PricingCalculationRequestBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(List<PricingCalculationRequestBeerDto> beers) {
        this.beers = beers;
    }
}
