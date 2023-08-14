package dto.order;

import java.util.List;

public class OrderRequestDto {
    private String consumerId;
    private List<OrderBeerRequestDto> beers;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

    public List<OrderBeerRequestDto> getBeers() {
        return beers;
    }

    public void setBeers(final List<OrderBeerRequestDto> beers) {
        this.beers = beers;
    }
}
