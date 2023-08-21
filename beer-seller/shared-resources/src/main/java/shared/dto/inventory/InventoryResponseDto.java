package shared.dto.inventory;

import java.util.List;

public class InventoryResponseDto {
    private List<InventoryBeerDto> beers;

    public InventoryResponseDto() {
    }

    public InventoryResponseDto(final List<InventoryBeerDto> beers) {
        this.beers = beers;
    }

    public List<InventoryBeerDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<InventoryBeerDto> beers) {
        this.beers = beers;
    }
}