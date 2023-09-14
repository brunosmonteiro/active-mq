package inventory.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.update.InventoryErrorDto;

import java.util.List;

@Component
public record InventoryErrorProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_DLQ = "inventory-dlq";

    public void sendMessage(final InventoryConsumptionErrorDto errorDto) {
        jmsTemplate.convertAndSend(INVENTORY_DLQ, errorDto);
    }

    public void sendMessage(final List<InventoryErrorDto> errorList) {
        jmsTemplate.convertAndSend(INVENTORY_DLQ, errorList);
    }
}
