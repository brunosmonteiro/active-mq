package shared.dto.inventory.validation;

import java.util.List;

public class InventoryValidationRequestDto {
    private String orderAggregationId;
    private String consumerId;
    private List<InventoryValidationRequestDetailDto> beers;

    public String getOrderAggregationId() {
        return orderAggregationId;
    }

    public void setOrderAggregationId(String orderAggregationId) {
        this.orderAggregationId = orderAggregationId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public List<InventoryValidationRequestDetailDto> getBeers() {
        return beers;
    }

    public void setBeers(List<InventoryValidationRequestDetailDto> beers) {
        this.beers = beers;
    }
}
