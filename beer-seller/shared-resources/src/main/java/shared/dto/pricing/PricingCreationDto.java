package shared.dto.pricing;

import java.math.BigDecimal;

public class PricingCreationDto {
    private Long beerId;
    private BigDecimal unitPrice;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
