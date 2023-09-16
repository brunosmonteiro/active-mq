package order.mapper;

import order.dto.OrderProcessedBeerDto;
import order.dto.OrderProcessedDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import shared.dto.inventory.validation.InventoryValidationRequestDto;
import shared.dto.order.creation.OrderBeerCreationDto;
import shared.dto.order.creation.OrderCreationDto;
import shared.dto.order.event.OrderBeerEventDto;
import shared.dto.order.event.OrderEventDto;
import shared.dto.order.request.OrderRequestDto;
import shared.dto.pricing.calculation.PricingCalculationRequestDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    InventoryValidationRequestDto toInventoryValidationRequestDto(
        final OrderRequestDto request,
        final String orderAggregationId
    );

    PricingCalculationRequestDto toPricingCalculationRequestDto(
        final OrderRequestDto request,
        final String orderAggregationId
    );

    @Mapping(target = "orderId", source = "orderCreationDto.orderId")
    @Mapping(target = "consumerId", source = "orderCreationDto.consumerId")
    @Mapping(target = "status", source = "orderCreationDto.status")
    @Mapping(target = "beers", ignore = true)
    OrderEventDto toOrderEventDto(
        final List<OrderProcessedBeerDto> orderProcessedBeerDtoList,
        final OrderCreationDto orderCreationDto
    );

    OrderCreationDto toOrderCreationDto(final OrderProcessedDto orderProcessed);

    List<OrderBeerCreationDto> toOrderBeerCreationDtoList(final List<OrderProcessedBeerDto> beers);

    @AfterMapping
    default void fillOrderEventDtoBeers(
            @MappingTarget final OrderEventDto eventDto,
            final List<OrderProcessedBeerDto> orderProcessedBeerDtoList) {
        final Map<Long, OrderProcessedBeerDto> orderProcessedBeerMap = orderProcessedBeerDtoList.stream()
            .collect(Collectors.toMap(OrderProcessedBeerDto::getBeerId, Function.identity()));
        eventDto.setBeers(
            orderProcessedBeerDtoList.stream().map(
                beer -> toOrderBeerEventDto(
                    beer,
                    orderProcessedBeerMap.get(beer.getBeerId()).getUnitPrice()
                )
            ).toList()
        );
    }

    OrderBeerEventDto toOrderBeerEventDto(
        final OrderProcessedBeerDto orderProcessedBeerDto,
        final BigDecimal unitPrice
    );
}
