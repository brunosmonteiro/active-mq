package order.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.pricing.calculation.PricingCalculationRequestDto;

@Component
public record PricingCalculationProducer(JmsTemplate jmsTemplate) {
    private static final String PRICING_CALCULATION_QUEUE = "pricing-calculation-queue";

    public void sendCalculationRequest(final PricingCalculationRequestDto pricingCalculationRequestDto) {
        jmsTemplate.convertAndSend(PRICING_CALCULATION_QUEUE, pricingCalculationRequestDto);
    }
}
