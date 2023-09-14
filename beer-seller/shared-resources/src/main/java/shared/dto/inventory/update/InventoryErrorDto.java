package shared.dto.inventory.update;

import shared.constants.inventory.InventoryActionType;

public class InventoryErrorDto {
    private InventoryActionType actionType;
    private Long beerId;
    private Long orderId;
    private String beerExternalId;
    private Integer requestedQuantity;
    private Integer availableQuantity;

    public InventoryActionType getActionType() {
        return actionType;
    }

    public void setActionType(final InventoryActionType actionType) {
        this.actionType = actionType;
    }

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getBeerExternalId() {
        return beerExternalId;
    }

    public void setBeerExternalId(String beerExternalId) {
        this.beerExternalId = beerExternalId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}

