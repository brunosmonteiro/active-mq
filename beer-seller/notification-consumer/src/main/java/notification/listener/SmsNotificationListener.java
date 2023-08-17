package notification.listener;

import notification.service.SmsNotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record SmsNotificationListener(SmsNotificationService smsNotificationService) {
    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory")
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        smsNotificationService.sendSms(order);
    }
}
