package shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.order.Order;
import shared.entity.order.OrderBeer;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toOrderResponseDto(final Order order);

    @Mapping(target = "id", source = "beer.id")
    @Mapping(target = "name", source = "beer.name")
    OrderBeerResponseDto toOrderBeerResponseDto(final OrderBeer orderBeer);

    OrderHistoryDto toOrderHistoryDto(final Order order);
}
