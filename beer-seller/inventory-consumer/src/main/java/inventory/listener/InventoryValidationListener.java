package inventory.listener;

import inventory.service.InventoryValidationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationRequestDto;

@Component
public record InventoryValidationListener(InventoryValidationService inventoryValidationService) {

    @JmsListener(destination = "inventory-validation-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final InventoryValidationRequestDto inventoryValidationRequestDto) {
        inventoryValidationService.validateStock(inventoryValidationRequestDto);
    }
}
