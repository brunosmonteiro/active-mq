package relay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import relay.entity.order.Order;
import relay.entity.order.OrderBeer;
import relay.mapper.OrderMapper;
import relay.repository.BeerRepository;
import relay.repository.OrderRepository;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;

import java.util.List;

@RestController
@RequestMapping("/orders")
public record OrderController(
        OrderRepository orderRepository,
        BeerRepository beerRepository,
        OrderMapper orderMapper) {

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody final OrderRequestDto orderRequestDto) {
        return orderMapper.toOrderResponseDto(mapOrder(orderRequestDto));
    }

    @GetMapping("/orders")
    public List<OrderHistoryDto> getOrderHistoryForConsumer(@RequestParam final String consumerId) {
        final var orders = orderRepository.findByConsumerId(consumerId);
        return orders.stream().map(orderMapper::toOrderHistoryDto).toList();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderDetail(@PathVariable final Long orderNumber) {
        return orderMapper.toOrderResponseDto(orderRepository.getReferenceById(orderNumber));
    }

    private Order mapOrder(final OrderRequestDto orderRequest) {
        final var order = new Order();
        order.setConsumerId(orderRequest.getConsumerId());
        orderRequest.getBeers().forEach(requestBeer -> {
            final var beer = beerRepository.findById(requestBeer.getId()).orElseThrow();
            order.addOrderBeer(new OrderBeer(beer, requestBeer.getQuantity()));
        });
        return order;
    }
}
