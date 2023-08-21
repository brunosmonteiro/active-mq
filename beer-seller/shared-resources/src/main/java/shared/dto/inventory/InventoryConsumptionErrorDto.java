package shared.dto.inventory;

public class InventoryConsumptionErrorDto {
    private String message;
    private Long orderId;
    private Integer available;
    private Integer requested;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(final Integer available) {
        this.available = available;
    }

    public Integer getRequested() {
        return requested;
    }

    public void setRequested(final Integer requested) {
        this.requested = requested;
    }
}
