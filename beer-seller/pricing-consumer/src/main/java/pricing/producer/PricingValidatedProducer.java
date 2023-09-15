package pricing.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.pricing.calculation.PricingCalculationResponseDto;

@Component
public record PricingValidatedProducer(JmsTemplate jmsTemplate) {
    private static final String PRICING_VALIDATED_QUEUE = "pricing-validated-queue";

    public void sendMessage(final PricingCalculationResponseDto response) {
        jmsTemplate.convertAndSend(PRICING_VALIDATED_QUEUE, response);
    }
}
