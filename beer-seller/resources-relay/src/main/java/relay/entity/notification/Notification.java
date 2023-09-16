package relay.entity.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import relay.entity.order.Order;
import shared.constants.notification.NotificationMethod;
import shared.constants.notification.NotificationStatus;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order order;
    private String text;
    @Enumerated(value = EnumType.STRING)
    private NotificationMethod method;
    @Enumerated(value = EnumType.STRING)
    private NotificationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public NotificationMethod getMethod() {
        return method;
    }

    public void setMethod(final NotificationMethod method) {
        this.method = method;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(final NotificationStatus status) {
        this.status = status;
    }
}
