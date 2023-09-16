package shared.dto.order.event;

import shared.constants.order.OrderStatus;

import java.util.List;

import java.util.List;

public class OrderEventDto {
    private Long orderId;
    private String consumerId;
    private OrderStatus status;
    private List<OrderBeerEventDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderBeerEventDto> getBeers() {
        return beers;
    }

    public void setBeers(List<OrderBeerEventDto> beers) {
        this.beers = beers;
    }
}
