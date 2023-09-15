package notification.listener;

import notification.service.NotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record NotificationListener(NotificationService notificationService) {
    private static final String NOTIFICATION_CONSUMER = "notification-listener";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = NOTIFICATION_CONSUMER)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        notificationService.processNotification(order);
    }
}
