package pricing.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pricing.service.PricingService;
import shared.dto.pricing.PricingRequestDto;

@Component
public record PricingValidationListener(PricingService pricingService) {

    @JmsListener(destination = "pricing-validation-queue", containerFactory = "queueListenerFactory")
    public void receiveConsumptionUpdate(final PricingRequestDto request) {
        pricingService.validatePricing(request);
    }
}