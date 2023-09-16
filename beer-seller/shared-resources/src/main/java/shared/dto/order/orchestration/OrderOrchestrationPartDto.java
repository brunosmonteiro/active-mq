package shared.dto.order.orchestration;

import java.util.List;

public abstract class OrderOrchestrationPartDto {
    protected String orderAggregationId;
    protected String consumerId;
    protected List<? extends OrderBeerOrchestrationPart> beers;

    public OrderOrchestrationPartDto(
            final String orderAggregationId,
            final String consumerId,
            final List<? extends OrderBeerOrchestrationPart> beers) {
        this.orderAggregationId = orderAggregationId;
        this.consumerId = consumerId;
        this.beers = beers;
    }

    public String getOrderAggregationId() {
        return orderAggregationId;
    }

    public void setOrderAggregationId(final String orderAggregationId) {
        this.orderAggregationId = orderAggregationId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

    public List<? extends OrderBeerOrchestrationPart> getBeers() {
        return beers;
    }

    public void setBeers(final List<? extends OrderBeerOrchestrationPart> beers) {
        this.beers = beers;
    }
}
