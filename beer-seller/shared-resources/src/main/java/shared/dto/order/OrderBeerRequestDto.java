package shared.dto.order;

public class OrderBeerRequestDto {
    private String beerId;
    private Integer quantity;

    public String getBeerId() {
        return beerId;
    }

    public void setBeerId(final String beerId) {
        this.beerId = beerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
