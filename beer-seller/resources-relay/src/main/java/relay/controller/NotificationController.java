package relay.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.mapper.NotificationMapper;
import relay.repository.NotificationRepository;
import relay.repository.OrderRepository;
import shared.client.NotificationClient;
import shared.dto.notification.NotificationCreationDto;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController implements NotificationClient {
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final OrderRepository orderRepository;

    public NotificationController(
            final NotificationMapper notificationMapper,
            final NotificationRepository notificationRepository,
            final OrderRepository orderRepository) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void createNotifications(
            @RequestBody final List<NotificationCreationDto> notificationCreationDtoList) {
        final var notifications = notificationCreationDtoList.stream().map(notificationCreationDto -> {
            final var notification = notificationMapper.toNotification(notificationCreationDto);
            notification.setOrder(orderRepository.findById(notificationCreationDto.getOrderId()).orElseThrow());
            return notification;
        }).toList();
        notificationRepository.saveAll(notifications);
    }
}
