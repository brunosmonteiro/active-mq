package destination.controller;

import destination.producer.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.order.request.OrderBeerRequestDto;
import shared.dto.order.request.OrderRequestDto;

import java.util.List;

@RestController
@RequestMapping("/orders")
public record OrderController(OrderProducer orderProducer) {

    @PostMapping("/external")
    public void publishEvent() {
        final var order = new OrderRequestDto();
        order.setConsumerId("C_001");
        order.setBeers(List.of(
            new OrderBeerRequestDto(1L, 2),
            new OrderBeerRequestDto(2L, 2),
            new OrderBeerRequestDto(3L, 2),
            new OrderBeerRequestDto(4L, 2),
            new OrderBeerRequestDto(5L, 2)
        ));
        orderProducer.sendMessage(order);
    }
}
