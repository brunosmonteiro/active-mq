package order.service;

import jakarta.transaction.Transactional;
import order.mapper.OrderMapper;
import order.producer.OrderProducer;
import order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import shared.dto.order.OrderHistoryDto;
import shared.dto.order.OrderRequestDto;
import shared.dto.order.OrderResponseDto;

import java.util.List;

@Service
public record OrderService(
        OrderProducer orderProducer,
        OrderMapper orderMapper,
        OrderRepository orderRepository) {
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

    public OrderHistoryDto getOrderDetail(final Long orderNumber) {
        return orderMapper.toOrderHistoryDto(orderRepository.getReferenceById(orderNumber));
    }
}
