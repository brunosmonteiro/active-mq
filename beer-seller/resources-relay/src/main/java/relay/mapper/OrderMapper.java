package relay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import relay.entity.order.Order;
import relay.entity.order.OrderBeer;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderResponseDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toOrderResponseDto(final Order order);

    @Mapping(target = "id", source = "beer.id")
    @Mapping(target = "name", source = "beer.name")
    OrderBeerResponseDto toOrderBeerResponseDto(final OrderBeer orderBeer);

    OrderHistoryDto toOrderHistoryDto(final Order order);
}
