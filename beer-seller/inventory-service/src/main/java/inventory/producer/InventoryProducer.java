package inventory.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import shared.dto.inventory.InventoryUpdateDto;

@Service
public record InventoryProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
    private static final String INVENTORY_STOCK_QUEUE = "inventory-stock-queue";

    public void sendOrder(final InventoryUpdateDto updateDto) {
        try {
            jmsTemplate.convertAndSend(INVENTORY_STOCK_QUEUE, objectMapper.writeValueAsString(updateDto));
        } catch (final JsonProcessingException e) {
            System.out.println("Error converting JSON");
        }
    }
}
