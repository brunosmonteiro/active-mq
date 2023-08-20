package shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shared.dto.order.OrderBeerRequestDto;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.order.Order;
import shared.entity.order.OrderBeer;

@Mapper(componentModel = "spring")
public abstract class OrderMapper extends BaseMapper<Order> {
    @Mapping(target = "beers", source = "beers")
    public abstract Order toOrder(final OrderRequestDto request);

    public abstract OrderResponseDto toOrderResponseDto(final Order order);

    public abstract OrderHistoryDto toOrderHistoryDto(final Order order);

    @Mapping(target = "id", ignore = true)
    public abstract OrderBeer toOrderBeer(final OrderBeerRequestDto orderBeerRequestDto);
}
