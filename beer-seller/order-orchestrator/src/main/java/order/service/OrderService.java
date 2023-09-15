package order.service;

import order.dto.OrderProcessedBeerDto;
import order.dto.OrderProcessedDto;
import order.producer.InventoryValidationProducer;
import order.producer.OrderProducer;
import order.producer.PricingCalculationProducer;
import org.springframework.stereotype.Service;
import shared.client.OrderClient;
import shared.constants.OrderBeerStatus;
import shared.constants.inventory.InventoryValidationStatus;
import shared.constants.order.OrderStatus;
import shared.constants.pricing.PricingStatus;
import shared.dto.order.creation.OrderBeerCreationDto;
import shared.dto.order.creation.OrderCreationDto;
import shared.dto.order.request.OrderRequestDto;

import java.util.List;

@Service
public record OrderService(
        PricingCalculationProducer pricingCalculationProducer,
        InventoryValidationProducer inventoryValidationProducer,
        OrderProducer orderProducer,
        OrderClient orderClient) {

    public void processOrder(final OrderRequestDto request) {
//        inventoryValidationProducer.sendValidationRequest();
//        pricingCalculationProducer.sendCalculationRequest();
    }

    public void createOrder(final OrderProcessedDto orderProcessedDto) {
        final var orderCreation = new OrderCreationDto();
        orderCreation.setOrderId(orderProcessedDto.getOrderId());
        orderCreation.setConsumerId(orderProcessedDto.getConsumerId());
        orderCreation.setTotalPrice(orderProcessedDto.getTotalPrice());
        orderCreation.setBeers(getOrderBeers(orderProcessedDto.getBeers()));
        orderCreation.setStatus(getOrderStatus(orderCreation.getBeers()));
        orderClient.createOrder(orderCreation);
//        orderProducer.sendOrder();
    }

    private List<OrderBeerCreationDto> getOrderBeers(final List<OrderProcessedBeerDto> beers) {
        return beers.stream().map(beer -> {
            final var orderBeer = new OrderBeerCreationDto();
            orderBeer.setBeerId(beer.getBeerId());
            orderBeer.setQuantity(beer.getQuantity());
            orderBeer.setStatus(getBeerStatus(beer));
            return orderBeer;
        }).toList();
    }

    private OrderBeerStatus getBeerStatus(final OrderProcessedBeerDto beer) {
        if (PricingStatus.INVALID.equals(beer.getPricingStatus())
                && InventoryValidationStatus.MISSING.equals(beer.getInventoryStatus())) {
            return OrderBeerStatus.MULTI_FAIL;
        } else if (PricingStatus.INVALID.equals(beer.getPricingStatus())) {
            return OrderBeerStatus.PRICING_FAIL;
        } else if (InventoryValidationStatus.MISSING.equals(beer.getInventoryStatus())) {
            return OrderBeerStatus.INVENTORY_UNAVAILABLE;
        } else if (InventoryValidationStatus.PARTIALLY_MISSING.equals(beer.getInventoryStatus())) {
            return OrderBeerStatus.INVENTORY_INCOMPLETE;
        }
        return OrderBeerStatus.VALID;
    }

    private OrderStatus getOrderStatus(final List<OrderBeerCreationDto> beers) {
        final var allStatuses = beers.stream().map(OrderBeerCreationDto::getStatus).toList();
        if (allStatuses.stream().allMatch(OrderBeerStatus::isInvalid)) {
            return OrderStatus.DENIED;
        } else if (allStatuses.stream().allMatch(OrderBeerStatus::isIncomplete)) {
            return OrderStatus.PARTIALLY_PLACED;
        } else {
            return OrderStatus.PLACED;
        }
    }
}
