package shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shared.entity.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
