package order.consumer;

import order.service.OrderService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.inventory.validation.InventoryValidationResponseDto;

import java.util.List;

@Component
public record InventoryValidatedListener(OrderService orderService) {

    @JmsListener(destination = "inventory-validated-queue", containerFactory = "queueListenerFactory")
    public void validateInventory(final List<InventoryValidationResponseDto> inventoryValidatedDtoList) {
        orderService.validateStock(inventoryValidationDto);
    }
}
