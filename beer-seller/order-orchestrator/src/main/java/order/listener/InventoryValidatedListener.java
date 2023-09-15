package order.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationResponseDto;

@Component
public record InventoryValidatedListener(MessageChannel messageChannel) {

    @JmsListener(destination = "inventory-validated-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final InventoryValidationResponseDto inventoryValidationResponseDto) {
        messageChannel.send(MessageBuilder.withPayload(inventoryValidationResponseDto).build());
    }
}
