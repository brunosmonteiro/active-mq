package shared.dto.inventory.update;

public class InventoryExternalUpdateDto {
    private String beerExternalId;
    private Integer quantity;

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
}
