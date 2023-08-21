package shared.mapper;

import org.mapstruct.Mapper;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.order.Order;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public abstract OrderResponseDto toOrderResponseDto(final Order order);

    public abstract OrderHistoryDto toOrderHistoryDto(final Order order);
}
