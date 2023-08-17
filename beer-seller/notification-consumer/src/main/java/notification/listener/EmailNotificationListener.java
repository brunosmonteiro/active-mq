package notification.listener;

import notification.service.EmailNotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record EmailNotificationListener(EmailNotificationService emailNotificationService) {
    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory")
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        emailNotificationService.sendEmail(order);
    }
}