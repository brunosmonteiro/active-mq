package order.dto;

import shared.constants.inventory.InventoryValidationStatus;
import shared.constants.pricing.PricingStatus;

import java.math.BigDecimal;

public class OrderProcessedBeerDto {
    private Long beerId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private InventoryValidationStatus inventoryStatus;
    private PricingStatus pricingStatus;

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

    public InventoryValidationStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(final InventoryValidationStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public PricingStatus getPricingStatus() {
        return pricingStatus;
    }

    public void setPricingStatus(final PricingStatus pricingStatus) {
        this.pricingStatus = pricingStatus;
    }
}
