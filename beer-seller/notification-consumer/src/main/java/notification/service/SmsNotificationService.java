package notification.service;

import jakarta.transaction.Transactional;
import shared.dto.order.OrderResponseDto;
import shared.entity.notification.NotificationMethod;
import shared.entity.notification.NotificationStatus;
import shared.mapper.NotificationMapper;
import shared.repository.NotificationRepository;

public record SmsNotificationService(
        NotificationMapper notificationMapper,
        NotificationRepository notificationRepository) {

    @Transactional
    public void sendSms(final OrderResponseDto order) {
        final var notification = notificationMapper.toNotification(order);
        notification.setText("This is a dummy sms send!");
        notification.setMethod(NotificationMethod.SMS);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }
}
