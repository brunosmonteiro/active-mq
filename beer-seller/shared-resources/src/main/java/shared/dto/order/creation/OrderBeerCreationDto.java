package shared.dto.order.creation;

import shared.constants.OrderBeerStatus;

public class OrderBeerCreationDto {
    private Long beerId;
    private Integer quantity;
    private OrderBeerStatus status;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public OrderBeerStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderBeerStatus status) {
        this.status = status;
    }
}
