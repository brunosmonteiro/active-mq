package shared.dto.order.creation;

import shared.constants.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreationDto {
    private Long orderId;
    private String consumerId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderBeerCreationDto> beers;

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public List<OrderBeerCreationDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<OrderBeerCreationDto> beers) {
        this.beers = beers;
    }
}
