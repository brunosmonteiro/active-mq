package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.List;

@Component
public record InventoryRegistryProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_CREATION_QUEUE = "inventory-creation-queue";

    public void sendMessage(final List<InventoryUpdateDto> inventoryUpdateDto) {
        jmsTemplate.convertAndSend(INVENTORY_CREATION_QUEUE, inventoryUpdateDto);
    }
}
