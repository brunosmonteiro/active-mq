package destination.controller;

import destination.producer.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.order.OrderRequestDto;

@RestController
@RequestMapping("/orders")
public record OrderController(OrderProducer orderProducer) {

    @PostMapping
    public void publishEvent(@RequestBody final OrderRequestDto orderRequestDto) {
        orderProducer.sendMessage(orderRequestDto);
    }
}
