package notification.service;

import jakarta.transaction.Transactional;
import shared.dto.order.OrderResponseDto;
import shared.entity.notification.Notification;
import shared.entity.notification.NotificationMethod;
import shared.entity.notification.NotificationStatus;
import shared.repository.NotificationRepository;
import shared.repository.OrderRepository;

public record SmsNotificationService(NotificationRepository notificationRepository, OrderRepository orderRepository) {

    @Transactional
    public void sendSms(final OrderResponseDto order) {
        final var notification = new Notification();
        notification.setOrder(orderRepository.findById(order.getId()).orElseThrow());
        notification.setText("This is a dummy sms send!");
        notification.setMethod(NotificationMethod.SMS);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }
}
