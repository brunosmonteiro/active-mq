package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderRequestDto;

@Component
public record OrderProducer(JmsTemplate jmsTemplate) {
    private static final String ORDER_PROCESSING_QUEUE = "order-processing-queue";

    public void sendMessage(final OrderRequestDto request) {
        jmsTemplate.convertAndSend(ORDER_PROCESSING_QUEUE, request);
    }
}
