package order.service;

import jakarta.transaction.Transactional;
import order.producer.OrderProducer;
import org.springframework.stereotype.Service;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.order.OrderBeerRequestDto;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.order.Order;
import shared.entity.order.OrderBeer;
import shared.entity.order.OrderStatus;
import shared.mapper.OrderMapper;
import shared.repository.BeerRepository;
import shared.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final BeerRepository beerRepository;

    public OrderService(
            final OrderProducer orderProducer,
            final OrderMapper orderMapper,
            final OrderRepository orderRepository,
            final InventoryClient inventoryClient,
            final BeerRepository beerRepository) {
        this.orderProducer = orderProducer;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.beerRepository = beerRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(final OrderRequestDto request) {
        final var order = mapOrder(request);
        final var beerIds = request.getBeers().stream().map(OrderBeerRequestDto::getId).collect(Collectors.toSet());
        final var inventory = inventoryClient.getInventory(beerIds);
        order.setTotal(getTotal(order, inventory));
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);

        final var response = orderMapper.toOrderResponseDto(order);
        orderProducer.sendOrder(response);
        return response;
    }

    public List<OrderHistoryDto> getOrderHistory(final String consumerId) {
        final var orders = orderRepository.findByConsumerId(consumerId);
        return orders.stream().map(orderMapper::toOrderHistoryDto).toList();
    }

    public OrderResponseDto getOrderDetail(final Long orderNumber) {
        return orderMapper.toOrderResponseDto(orderRepository.getReferenceById(orderNumber));
    }

    private BigDecimal getTotal(final Order order, final InventoryResponseDto inventory) {
        final var beerMap = inventory.getBeers().stream().collect(
            Collectors.toMap(InventoryBeerDto::getId, InventoryBeerDto::getPrice));

        return order.getBeers().stream()
            .map(b -> beerMap.get(b.getBeer().getId()).multiply(BigDecimal.valueOf(b.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
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
