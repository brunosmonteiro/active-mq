package pricing.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.pricing.PricingResponseDto;

@Component
public record PricingValidatedProducer(JmsTemplate jmsTemplate) {
    private static final String PRICING_VALIDATED_QUEUE = "pricing-validated-queue";

    public void sendMessage(final PricingResponseDto response) {
        jmsTemplate.convertAndSend(PRICING_VALIDATED_QUEUE, response);
    }
}
