package shared.dto.inventory.validation;

import java.util.List;

public class InventoryValidationResponseDto {
    private Long orderId;
    private List<InventoryValidationResponseDetailDto> beers;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<InventoryValidationResponseDetailDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<InventoryValidationResponseDetailDto> beers) {
        this.beers = beers;
    }
}
