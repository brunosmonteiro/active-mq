package shared.dto.order.orchestration;

import shared.dto.order.orchestration.OrderBeerOrchestrationPart;

import java.util.List;

public abstract class OrderOrchestrationPartDto {
    protected Long orderId;
    protected String consumerId;
    protected List<? extends OrderBeerOrchestrationPart> beers;

    public OrderOrchestrationPartDto(
            final Long orderId,
            final String consumerId,
            final List<? extends OrderBeerOrchestrationPart> beers) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.beers = beers;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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
