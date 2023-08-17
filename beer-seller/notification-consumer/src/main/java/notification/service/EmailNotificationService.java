package notification.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderResponseDto;
import shared.entity.notification.NotificationMethod;
import shared.entity.notification.NotificationStatus;
import shared.mapper.NotificationMapper;
import shared.repository.NotificationRepository;

@Service
public record EmailNotificationService(
        NotificationMapper notificationMapper,
        NotificationRepository notificationRepository) {

    @Transactional
    public void sendEmail(final OrderResponseDto order) {
        final var notification = notificationMapper.toNotification(order);
        notification.setText("This is a dummy email send!");
        notification.setMethod(NotificationMethod.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }
}
