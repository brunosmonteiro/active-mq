package shared.dto.inventory.validation;

import java.util.List;

public class InventoryValidationRequestDto {
    private Long orderId;
    private List<InventoryValidationRequestDetailDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<InventoryValidationRequestDetailDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<InventoryValidationRequestDetailDto> beers) {
        this.beers = beers;
    }
}
