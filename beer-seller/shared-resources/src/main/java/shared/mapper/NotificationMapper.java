package shared.mapper;

import shared.dto.order.OrderResponseDto;
import shared.entity.notification.Notification;

public interface NotificationMapper {
    Notification toNotification(final OrderResponseDto order);
}
