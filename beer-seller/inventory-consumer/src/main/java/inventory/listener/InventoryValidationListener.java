package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationRequestDto;

@Component
public record InventoryValidationListener(InventoryService inventoryService) {

    @JmsListener(destination = "inventory-validation-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final InventoryValidationRequestDto inventoryValidationDto) {
        inventoryService.validateStock(inventoryValidationDto);
    }
}
