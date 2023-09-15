package relay.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.entity.order.Order;
import relay.entity.order.OrderBeer;
import relay.mapper.OrderMapper;
import relay.repository.BeerRepository;
import relay.repository.OrderRepository;
import shared.client.OrderClient;
import shared.dto.order.history.OrderHistoryDto;
import shared.dto.order.creation.OrderCreationDto;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController implements OrderClient {
    private final OrderRepository orderRepository;
    private final BeerRepository beerRepository;
    private final OrderMapper orderMapper;

    public OrderController(
            final OrderRepository orderRepository,
            final BeerRepository beerRepository,
            final OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.beerRepository = beerRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void createOrder(@RequestBody OrderCreationDto orderCreationDto) {
        orderRepository.save(mapOrder(orderCreationDto));
    }

    @GetMapping("/orders")
    public List<OrderHistoryDto> getOrderHistoryForConsumer(@RequestParam final String consumerId) {
        final var orders = orderRepository.findByConsumerId(consumerId);
        return orders.stream().map(orderMapper::toOrderHistoryDto).toList();
    }

//    @GetMapping("/{id}")
//    public OrderResponseDto getOrderDetail(@PathVariable final Long orderNumber) {
//        return orderMapper.toOrderResponseDto(orderRepository.getReferenceById(orderNumber));
//    }

    private Order mapOrder(final OrderCreationDto orderCreationDto) {
        final var order = new Order();
        order.setConsumerId(orderCreationDto.getConsumerId());
        orderCreationDto.getBeers().forEach(orderBeer -> {
            final var beer = beerRepository.findById(orderBeer.getBeerId()).orElseThrow();
            order.addOrderBeer(new OrderBeer(beer, orderBeer.getQuantity(), orderBeer.getStatus()));
        });
        return order;
    }
}
