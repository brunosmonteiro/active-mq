package relay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.mapper.NotificationMapper;
import relay.repository.NotificationRepository;
import relay.repository.OrderRepository;
import shared.dto.notification.NotificationCreationDto;

@RestController
@RequestMapping("/notifications")
public record NotificationController(
        NotificationMapper notificationMapper,
        NotificationRepository notificationRepository,
        OrderRepository orderRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNotification(final NotificationCreationDto notificationCreationDto) {
        final var notification = notificationMapper.toNotification(notificationCreationDto);
        notification.setOrder(orderRepository.findByOrderId(notificationCreationDto.getOrderId()));
        notificationRepository.save(notification);
    }
}
