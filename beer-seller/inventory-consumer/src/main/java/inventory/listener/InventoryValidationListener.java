package inventory.listener;

import inventory.service.InventoryUpdateService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationRequestDto;

@Component
public record InventoryValidationListener(InventoryUpdateService inventoryService) {

    @JmsListener(destination = "inventory-validation-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final InventoryValidationRequestDto inventoryValidationRequestDto) {
        inventoryService.validateStock(inventoryValidationRequestDto);
    }
}
