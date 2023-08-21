package order.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderResponseDto;

@Component
public record OrderProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
    private static final String ORDERS_TOPIC = "orders-topic";

    public void sendOrder(final OrderResponseDto order) {
        try {
            jmsTemplate.convertAndSend(ORDERS_TOPIC, objectMapper.writeValueAsString(order));
        } catch (final JsonProcessingException e) {

        }
    }
}
