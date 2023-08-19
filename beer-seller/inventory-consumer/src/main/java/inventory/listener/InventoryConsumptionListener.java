package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record InventoryConsumptionListener(InventoryService inventoryService) {
    @JmsListener(destination = "orders-topic", containerFactory = "queueListenerFactory")
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        inventoryService.consumeStock(order);
    }
}