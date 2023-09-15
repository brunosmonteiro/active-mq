package error.listener;

import error.service.ErrorService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public record InventoryErrorListener(ErrorService errorService) {
    @JmsListener(destination = "inventory-dlq", containerFactory = "queueListenerFactory")
    public void registerError(final String message) {
        errorService.storeMessage(message);
    }
}
