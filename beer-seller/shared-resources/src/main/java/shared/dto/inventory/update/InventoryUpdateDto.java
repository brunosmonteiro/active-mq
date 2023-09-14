package shared.dto.inventory.update;

public class InventoryUpdateDto {
    private String beerExternalId;
    private Long beerId;
    private Integer quantity;

    public InventoryUpdateDto(final Long beerId, final Integer quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

    public InventoryUpdateDto(final String beerExternalId, final Integer quantity) {
        this.beerExternalId = beerExternalId;
        this.quantity = quantity;
    }

    public String getBeerExternalId() {
        return beerExternalId;
    }

    public void setBeerExternalId(final String beerExternalId) {
        this.beerExternalId = beerExternalId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }
}
