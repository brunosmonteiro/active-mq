package shared.dto.order.request;

public class OrderBeerRequestDto {
    private Long beerId;
    private Integer quantity;

    public OrderBeerRequestDto() {
    }

    public OrderBeerRequestDto(final Long beerId, final Integer quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

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
}
