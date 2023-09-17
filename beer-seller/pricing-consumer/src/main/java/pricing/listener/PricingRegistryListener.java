package pricing.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.client.PricingClient;
import shared.dto.pricing.PricingRegistryDto;

import java.util.List;

@Component
public record PricingRegistryListener(PricingClient pricingClient) {

    @JmsListener(destination = "pricing-registry-queue", containerFactory = "queueListenerFactory")
    public void receiveConsumptionUpdate(final List<PricingRegistryDto> pricingRegistryDtoList) {
        pricingClient.registerPrices(pricingRegistryDtoList);
    }
}
