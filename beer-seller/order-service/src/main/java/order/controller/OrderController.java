package order.controller;

import order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;

import java.util.List;

@RestController("/orders")
public record OrderController(OrderService orderService) {
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody final OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public List<OrderHistoryDto> getOrderHistoryForConsumer(@RequestParam final String consumerId) {
        return orderService.getOrderHistory(consumerId);
    }

    @GetMapping("/{id}")
    public OrderHistoryDto getOrderDetail(@PathVariable final Long orderNumber) {
        return orderService.getOrderDetail(orderNumber);
    }
}
