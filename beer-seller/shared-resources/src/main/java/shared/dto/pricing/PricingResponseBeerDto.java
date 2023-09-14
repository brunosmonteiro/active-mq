package shared.dto.pricing;

import shared.constants.pricing.PricingStatus;

import java.math.BigDecimal;

public class PricingResponseBeerDto {
    private Long beerId;
    private Integer quantity;
    private PricingStatus status;
    private BigDecimal value;

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

    public PricingStatus getStatus() {
        return status;
    }

    public void setStatus(final PricingStatus status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }
}
