package relay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import relay.entity.order.Order;
import relay.entity.order.OrderBeer;
import shared.dto.order.history.OrderHistoryDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderHistoryDto toOrderHistoryDto(final Order order);
}
