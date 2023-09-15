package notification.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public class NotificationListener {
    private static final String NOTIFICATION_CONSUMER = "notification-listener";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = NOTIFICATION_CONSUMER)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        emailNotificationService.sendEmail(order);
    }
}
