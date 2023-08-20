package order.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderResponseDto;

@Component
public record OrderProducer(JmsTemplate jmsTemplate) {
    private static final String ORDERS_TOPIC = "orders-topic";

    public void sendOrder(final OrderResponseDto order) {
        jmsTemplate.convertAndSend(ORDERS_TOPIC, order);
    }
}
