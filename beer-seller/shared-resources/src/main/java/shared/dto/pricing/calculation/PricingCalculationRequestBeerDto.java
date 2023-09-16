package shared.dto.pricing.calculation;

public class PricingCalculationRequestBeerDto {
    private Long beerId;
    private Integer quantity;

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
}

