package order.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import shared.dto.pricing.calculation.PricingCalculationResponseDto;

@Component
public record PricingCalculatedListener(MessageChannel messageChannel) {

    @JmsListener(destination = "pricing-calculated-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final PricingCalculationResponseDto pricingCalculationResponseDto) {
        messageChannel.send(MessageBuilder.withPayload(pricingCalculationResponseDto).build());
    }
}
