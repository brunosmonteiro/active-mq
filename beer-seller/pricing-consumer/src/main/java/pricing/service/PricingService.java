package pricing.service;

import org.springframework.stereotype.Service;
import pricing.producer.PricingErrorProducer;
import pricing.producer.PricingValidatedProducer;
import shared.dto.pricing.PricingRequestDto;

@Service
public record PricingService(
        PricingValidatedProducer pricingValidatedProducer,
        PricingErrorProducer pricingErrorProducer) {
    public void validatePricing(final PricingRequestDto request) {

    }
}
