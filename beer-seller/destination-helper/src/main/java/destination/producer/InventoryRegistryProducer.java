package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.update.InventoryUpsertDto;

import java.util.List;

@Component
public record InventoryRegistryProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_STOCK_QUEUE = "inventory-stock-queue";

    public void sendMessage(final List<InventoryUpsertDto> update) {
        jmsTemplate.convertAndSend(INVENTORY_STOCK_QUEUE, update);
    }
}
