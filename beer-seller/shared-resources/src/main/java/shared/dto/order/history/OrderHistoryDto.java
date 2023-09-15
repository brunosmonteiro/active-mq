package shared.dto.order.history;

import shared.constants.order.OrderStatus;

import java.math.BigDecimal;

public class OrderHistoryDto {
    private Long id;
    private BigDecimal total;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }
}
