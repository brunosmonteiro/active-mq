package pricing.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pricing.service.PricingService;
import shared.dto.pricing.calculation.PricingCalculationRequestDto;

@Component
public record PricingValidationListener(PricingService pricingService) {

    @JmsListener(destination = "pricing-validation-queue", containerFactory = "queueListenerFactory")
    public void receiveConsumptionUpdate(final PricingCalculationRequestDto request) {
        pricingService.validatePricing(request);
    }
}