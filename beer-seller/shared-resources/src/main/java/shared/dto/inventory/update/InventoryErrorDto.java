package shared.dto.inventory.update;

import shared.constants.inventory.InventoryActionType;

public class InventoryErrorDto {
    private InventoryActionType actionType;
    private Long beerId;
    private Long orderId;
    private String beerExternalId;
    private Integer requestedQuantity;
    private Integer availableQuantity;
    private String details;

    public InventoryErrorDto() {
    }

    public InventoryErrorDto(
            final InventoryActionType actionType,
            final String beerExternalId,
            final String details) {
        this.actionType = actionType;
        this.beerExternalId = beerExternalId;
        this.details = details;
    }

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

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }
}

