package pricing.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.PricingClient;
import shared.dto.pricing.PricingCreationDto;

import java.util.List;

@Component
public record PricingCreationListener(PricingClient pricingClient) {

    @JmsListener(destination = "pricing-validation-queue", containerFactory = "queueListenerFactory")
    public void receiveConsumptionUpdate(final List<PricingCreationDto> pricingCreationDtoList) {
        pricingClient.registerPrices(pricingCreationDtoList);
    }
}
