package order.service;

import jakarta.transaction.Transactional;
import order.producer.OrderProducer;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;
import shared.mapper.OrderMapper;
import shared.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderService(OrderProducer orderProducer, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderProducer = orderProducer;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(final OrderRequestDto request) {
        final var order = orderRepository.save(orderMapper.toOrder(request));
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
}
