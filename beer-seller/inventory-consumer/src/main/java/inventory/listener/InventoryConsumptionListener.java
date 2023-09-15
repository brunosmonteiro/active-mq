package inventory.listener;

import inventory.service.InventoryUpdateService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public record InventoryConsumptionListener(InventoryUpdateService inventoryUpdateService) {
    private static final String INVENTORY_CONSUMPTION_SUBSCRIPTION = "inventory-consumption";

    @JmsListener(destination = "orders-topic", containerFactory = "topicListenerFactory", subscription = INVENTORY_CONSUMPTION_SUBSCRIPTION)
    public void receiveConsumptionUpdate(final OrderResponseDto order) {
        inventoryUpdateService.consumeStock(order);
    }
}