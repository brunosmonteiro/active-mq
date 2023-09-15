package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shared.dto.notification.NotificationCreationDto;

import java.util.List;

@FeignClient(value = "NotificationClient", url = "${url.resources-relay}")
@RequestMapping("/notifications")
public interface NotificationClient {
    @PostMapping
    void createNotifications(@RequestBody final List<NotificationCreationDto> notificationCreationDtoList);
}
