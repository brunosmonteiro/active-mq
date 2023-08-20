package inventory.listener;

import inventory.service.InventoryService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.OrderResponseDto;

@Component
public record InventoryConsumptionListener(InventoryService inventoryService) {
    private static final String INVENTORY_CONSUMPTION_SUBSCRIPTION = "inventory-consumption";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = INVENTORY_CONSUMPTION_SUBSCRIPTION)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        inventoryService.consumeStock(order);
    }
}