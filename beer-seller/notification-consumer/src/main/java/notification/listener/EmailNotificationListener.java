package notification.listener;

import notification.service.EmailNotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record EmailNotificationListener(EmailNotificationService emailNotificationService) {
    private static final String EMAIL_NOTIFICATION_CONSUMER = "email-notification";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = EMAIL_NOTIFICATION_CONSUMER)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        emailNotificationService.sendEmail(order);
    }
}