package shared.dto.pricing.calculation;

import shared.constants.pricing.PricingStatus;

import java.math.BigDecimal;

public class PricingCalculationResponseBeerDto {
    private Long beerId;
    private Integer quantity;
    private PricingStatus status;
    private BigDecimal unitPrice;

    public PricingCalculationResponseBeerDto(
            final Long beerId,
            final Integer quantity,
            final PricingStatus status,
            final BigDecimal unitPrice) {
        this.beerId = beerId;
        this.quantity = quantity;
        this.status = status;
        this.unitPrice = unitPrice;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public PricingStatus getStatus() {
        return status;
    }

    public void setStatus(final PricingStatus status) {
        this.status = status;
    }
}
