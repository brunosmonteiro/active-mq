package notification.listener;

import notification.service.SmsNotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record SmsNotificationListener(SmsNotificationService smsNotificationService) {
    private static final String SMS_NOTIFICATION_CONSUMER = "sms-notification";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = SMS_NOTIFICATION_CONSUMER)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        smsNotificationService.sendSms(order);
    }
}
