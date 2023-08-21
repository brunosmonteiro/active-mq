package notification.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderResponseDto;
import shared.entity.notification.Notification;
import shared.entity.notification.NotificationMethod;
import shared.entity.notification.NotificationStatus;
import shared.repository.NotificationRepository;
import shared.repository.OrderRepository;

@Service
public record EmailNotificationService(NotificationRepository notificationRepository, OrderRepository orderRepository) {

    @Transactional
    public void sendEmail(final OrderResponseDto order) {
        final var notification = new Notification();
        notification.setOrder(orderRepository.findById(order.getId()).orElseThrow());
        notification.setText("This is a dummy email send!");
        notification.setMethod(NotificationMethod.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }
}
