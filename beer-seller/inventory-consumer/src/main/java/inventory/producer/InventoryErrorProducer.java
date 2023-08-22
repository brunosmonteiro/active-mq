package inventory.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.InventoryConsumptionErrorDto;

@Component
public record InventoryErrorProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_DLQ = "inventory-dlq";

    public void sendMessage(final InventoryConsumptionErrorDto errorDto) {
        jmsTemplate.convertAndSend(INVENTORY_DLQ, errorDto);
    }
}
