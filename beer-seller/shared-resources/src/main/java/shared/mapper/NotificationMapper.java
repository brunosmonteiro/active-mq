package shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shared.dto.order.OrderResponseDto;
import shared.entity.notification.Notification;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface NotificationMapper {
    @Mapping(target = "order", source = "id")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    Notification toNotification(final OrderResponseDto order);
}
