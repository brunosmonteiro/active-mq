package notification.service;

import notification.producer.NotificationErrorProducer;
import org.springframework.stereotype.Service;
import shared.client.NotificationClient;
import shared.constants.notification.NotificationMethod;
import shared.constants.notification.NotificationStatus;
import shared.dto.notification.NotificationCreationDto;
import shared.dto.notification.NotificationErrorDto;
import shared.dto.order.event.OrderEventDto;

import java.util.Arrays;

@Service
public record NotificationService(
        NotificationClient notificationClient,
        NotificationErrorProducer notificationErrorProducer) {

    public void processNotification(final OrderEventDto order) {
        try {
            notificationClient.createNotifications(
                Arrays.asList(
                    getEmailNotification(order),
                    getSmsNotification(order)
                )
            );
        } catch (final Exception e) {
            notificationErrorProducer.sendMessage(new NotificationErrorDto(order.getOrderId(), e.getMessage()));
        }
    }

    private NotificationCreationDto getEmailNotification(final OrderEventDto order) {
        final var email = new NotificationCreationDto();
        email.setMethod(NotificationMethod.EMAIL);
        email.setStatus(NotificationStatus.SENT);
        email.setOrderId(order.getOrderId());
        email.setText("This is a dummy email for client " + order.getConsumerId());
        return email;
    }

    private NotificationCreationDto getSmsNotification(final OrderEventDto order) {
        final var sms = new NotificationCreationDto();
        sms.setMethod(NotificationMethod.SMS);
        sms.setStatus(NotificationStatus.SENT);
        sms.setOrderId(order.getOrderId());
        sms.setText("This is a dummy sms for client " + order.getConsumerId());
        return sms;
    }
}
