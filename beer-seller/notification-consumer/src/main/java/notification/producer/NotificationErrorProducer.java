package notification.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.notification.NotificationErrorDto;

@Component
public record NotificationErrorProducer(JmsTemplate jmsTemplate) {
    private static final String NOTIFICATION_DLQ = "notification-dlq";

    public void sendMessage(final NotificationErrorDto notificationErrorDto) {
        jmsTemplate.convertAndSend(NOTIFICATION_DLQ, notificationErrorDto);
    }
}
