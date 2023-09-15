package order.service;

import order.dto.OrderProcessedBeerDto;
import order.dto.OrderProcessedDto;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.stereotype.Service;
import shared.dto.inventory.validation.InventoryValidationResponseDetailDto;
import shared.dto.inventory.validation.InventoryValidationResponseDto;
import shared.dto.order.orchestration.OrderOrchestrationPartDto;
import shared.dto.pricing.calculation.PricingCalculationResponseBeerDto;
import shared.dto.pricing.calculation.PricingCalculationResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderAggregatorService {
    @Aggregator(inputChannel = "aggregatorChannel", outputChannel = "orderProcessedChannel")
    public OrderProcessedDto aggregate(final List<OrderOrchestrationPartDto> orderParts) {
        final var orderProcessed = new OrderProcessedDto();
        final Map<Long, OrderProcessedBeerDto> beerMap = new HashMap<>();
        orderParts.forEach(orderPart -> {
            orderProcessed.setOrderId(orderPart.getOrderId());
            orderProcessed.setConsumerId(orderPart.getConsumerId());
            if (orderPart instanceof final InventoryValidationResponseDto orderInventory) {
                fillInventoryOrder(orderInventory, beerMap);
            }
            if (orderPart instanceof final PricingCalculationResponseDto orderPricing) {
                orderProcessed.setTotalPrice(orderPricing.getTotalPrice());
                fillPricingOrder(orderPricing, beerMap);
            }
        });
        orderProcessed.setBeers((List<OrderProcessedBeerDto>) beerMap.values());
        return orderProcessed;
    }

    private void fillInventoryOrder(
            final InventoryValidationResponseDto orderInventory,
            final Map<Long, OrderProcessedBeerDto> beerMap) {
        for (var beer : orderInventory.getBeers()) {
            var orderBeer = beerMap.getOrDefault(
                beer.getBeerId(),
                new OrderProcessedBeerDto()
            );
            final var inventoryBeer = (InventoryValidationResponseDetailDto) beer;
            orderBeer.setBeerId(inventoryBeer.getBeerId());
            orderBeer.setInventoryStatus(inventoryBeer.getStatus());
        }
    }

    private void fillPricingOrder(
            final PricingCalculationResponseDto orderPricing,
            final Map<Long, OrderProcessedBeerDto> beerMap) {
        for (var beer : orderPricing.getBeers()) {
            var orderBeer = beerMap.getOrDefault(
                beer.getBeerId(),
                new OrderProcessedBeerDto()
            );
            final var pricingBeer = (PricingCalculationResponseBeerDto) beer;
            orderBeer.setBeerId(pricingBeer.getBeerId());
            orderBeer.setPricingStatus(pricingBeer.getStatus());
            orderBeer.setUnitPrice(pricingBeer.getUnitPrice());
        }
    }
}
