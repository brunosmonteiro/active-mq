package order.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.order.event.OrderEventDto;

@Component
public record OrderProducer(JmsTemplate jmsTemplate) {
    private static final String ORDERS_TOPIC = "orders-topic";

    public void sendOrder(final OrderEventDto order) {
        jmsTemplate.convertAndSend(ORDERS_TOPIC, order);
    }
}
