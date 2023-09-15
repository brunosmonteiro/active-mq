package shared.dto.inventory.validation;

import java.util.List;

public class InventoryValidationRequestDto {
    private Long orderId;
    private String consumerId;
    private List<InventoryValidationRequestDetailDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

    public List<InventoryValidationRequestDetailDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<InventoryValidationRequestDetailDto> beers) {
        this.beers = beers;
    }
}
