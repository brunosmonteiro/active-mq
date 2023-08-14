package dto.order;

import entity.order.OrderBeer;
import entity.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDto {
    private Long id;
    private BigDecimal total;
    private List<OrderBeerResponseDto> beers;
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public List<OrderBeerResponseDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<OrderBeerResponseDto> beers) {
        this.beers = beers;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }
}
