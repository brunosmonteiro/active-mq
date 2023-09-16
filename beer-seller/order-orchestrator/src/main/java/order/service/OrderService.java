package order.service;

import order.dto.OrderProcessedBeerDto;
import order.dto.OrderProcessedDto;
import order.mapper.OrderMapper;
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
import shared.dto.order.request.OrderRequestDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public record OrderService(
        PricingCalculationProducer pricingCalculationProducer,
        InventoryValidationProducer inventoryValidationProducer,
        OrderMapper orderMapper,
        OrderProducer orderProducer,
        OrderClient orderClient) {

    public void processOrder(final OrderRequestDto request) {
        final String orderAggregationId = UUID.randomUUID().toString();
        inventoryValidationProducer.sendValidationRequest(
            orderMapper.toInventoryValidationRequestDto(request, orderAggregationId));
        pricingCalculationProducer.sendCalculationRequest(
            orderMapper.toPricingCalculationRequestDto(request, orderAggregationId));
    }

    public void createOrder(final OrderProcessedDto orderProcessed) {
        final var orderCreation = orderMapper.toOrderCreationDto(orderProcessed);
        setBeerStatuses(orderCreation.getBeers(), orderProcessed.getBeers());
        orderCreation.setStatus(getOrderStatus(orderCreation.getBeers()));
        orderClient.createOrder(orderCreation);
        orderProducer.sendOrder(orderMapper.toOrderEventDto(orderProcessed.getBeers(), orderCreation));
    }

    private void setBeerStatuses(
            final List<OrderBeerCreationDto> beers,
            final List<OrderProcessedBeerDto> orderProcessedBeers) {
        final Map<Long, OrderProcessedBeerDto> orderProcessedBeerMap = orderProcessedBeers.stream()
            .collect(Collectors.toMap(OrderProcessedBeerDto::getBeerId, Function.identity()));
        beers.forEach(beer -> beer.setStatus(getBeerStatus(orderProcessedBeerMap.get(beer.getBeerId()))));
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
