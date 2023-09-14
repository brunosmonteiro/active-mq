package shared.dto.inventory.validation;

public class InventoryValidatedDto {
    private Long orderId;
    private InventoryValidationStatus status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public InventoryValidationStatus getStatus() {
        return status;
    }

    public void setStatus(final InventoryValidationStatus status) {
        this.status = status;
    }
}
