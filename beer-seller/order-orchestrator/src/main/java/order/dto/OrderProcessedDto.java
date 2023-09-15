package order.dto;

import shared.constants.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderProcessedDto {
    private Long orderId;
    private String consumerId;
    private BigDecimal totalPrice;
    private List<OrderProcessedBeerDto> beers;

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

    public List<OrderProcessedBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<OrderProcessedBeerDto> beers) {
        this.beers = beers;
    }
}
