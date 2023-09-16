package order.listener;

import order.service.OrderService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import shared.dto.order.request.OrderRequestDto;

@Component
public record OrderProcessingListener(OrderService orderService) {

    @JmsListener(destination = "order-processing-queue", containerFactory = "queueListenerFactory")
    public void processOrder(final OrderRequestDto request) {
        orderService.processOrder(request);
    }
}
