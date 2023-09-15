package relay.mapper;

import org.mapstruct.Mapper;
import relay.entity.notification.Notification;
import shared.dto.notification.NotificationCreationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(final NotificationCreationDto notificationCreationDto);
}
