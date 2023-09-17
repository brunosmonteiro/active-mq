package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.pricing.PricingRegistryDto;

import java.util.List;

@Component
public record PricingRegistryProducer(JmsTemplate jmsTemplate) {
    private static final String PRICING_REGISTRY_QUEUE = "pricing-registry-queue";

    public void sendMessage(final List<PricingRegistryDto> pricingRegistryDto) {
        jmsTemplate.convertAndSend(PRICING_REGISTRY_QUEUE, pricingRegistryDto);
    }
}
