package shared.dto.inventory;

import shared.dto.beer.BeerDto;

public class InventoryCreationDto {
    private BeerDto beer;

    public BeerDto getBeer() {
        return beer;
    }

    public void setBeer(final BeerDto beer) {
        this.beer = beer;
    }
}
