package destination.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.update.InventoryUpdateDto;

@Component
public record InventoryExternalProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_STOCK_QUEUE = "inventory-stock-queue";

    public void sendMessage(final InventoryUpdateDto updateDto) {
        jmsTemplate.convertAndSend(INVENTORY_STOCK_QUEUE, updateDto);
    }
}
