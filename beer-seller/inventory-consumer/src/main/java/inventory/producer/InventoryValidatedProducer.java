package inventory.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationResponseDto;

@Component
public record InventoryValidatedProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_VALIDATED_QUEUE = "inventory-validated-queue";

    public void sendMessage(final InventoryValidationResponseDto response) {
        jmsTemplate.convertAndSend(INVENTORY_VALIDATED_QUEUE, response);
    }
}
