package shared.dto.inventory;

import java.util.List;

public class InventoryConsumptionErrorDto {
    private Long orderId;
    private InventoryActionType actionType;
    private List<InventoryBeerConsumptionErrorDto> beerConsumption;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public InventoryActionType getActionType() {
        return actionType;
    }

    public void setActionType(final InventoryActionType actionType) {
        this.actionType = actionType;
    }

    public List<InventoryBeerConsumptionErrorDto> getBeerConsumption() {
        return beerConsumption;
    }

    public void setBeerConsumption(final List<InventoryBeerConsumptionErrorDto> beerConsumption) {
        this.beerConsumption = beerConsumption;
    }
}
