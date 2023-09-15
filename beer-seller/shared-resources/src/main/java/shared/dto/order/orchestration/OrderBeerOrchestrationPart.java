package shared.dto.order.orchestration;

public abstract class OrderBeerOrchestrationPart {
    protected Long beerId;

    public OrderBeerOrchestrationPart(final Long beerId) {
        this.beerId = beerId;
    }

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }
}
