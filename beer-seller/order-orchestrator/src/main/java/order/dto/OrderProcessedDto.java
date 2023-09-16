package order.dto;

import shared.constants.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderProcessedDto {
    private String orderAggregationId;
    private String consumerId;
    private BigDecimal totalPrice;
    private List<OrderProcessedBeerDto> beers;

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
