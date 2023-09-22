package relay.mapper;

import org.mapstruct.Mapper;
import relay.entity.order.Order;
import shared.dto.order.history.OrderHistoryDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderHistoryDto toOrderHistoryDto(final Order order);
}
