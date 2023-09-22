package shared.dto.order.event;

import shared.constants.OrderBeerStatus;

import java.math.BigDecimal;

public class OrderBeerEventDto {
    private Long beerId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private OrderBeerStatus status;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderBeerStatus getStatus() {
        return status;
    }

    public void setStatus(OrderBeerStatus status) {
        this.status = status;
    }
}
