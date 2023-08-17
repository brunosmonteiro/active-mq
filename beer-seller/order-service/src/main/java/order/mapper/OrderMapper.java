package order.mapper;

import org.aspectj.weaver.ast.Or;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.order.Order;

public interface OrderMapper {
    Order toOrder(final OrderRequestDto request);
    OrderResponseDto toOrderResponseDto(final Order order);
    OrderHistoryDto toOrderHistoryDto(final Order order);
}
