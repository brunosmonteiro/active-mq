package order.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationRequestDetailDto;

import java.util.List;

@Component
public record InventoryValidationProducer(JmsTemplate jmsTemplate) {
    private static final String INVENTORY_VALIDATION_QUEUE = "inventory-validation-queue";

    public void sendValidationRequest(final List<InventoryValidationRequestDetailDto> inventoryValidationDtoList) {
        jmsTemplate.convertAndSend(INVENTORY_VALIDATION_QUEUE, inventoryValidationDtoList);
    }
}
