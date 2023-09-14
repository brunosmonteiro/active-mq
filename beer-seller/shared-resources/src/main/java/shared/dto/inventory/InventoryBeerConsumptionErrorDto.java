package shared.dto.inventory;

public class InventoryBeerConsumptionErrorDto {
    private Long beerId;
    private Integer available;
    private Integer requested;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
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
