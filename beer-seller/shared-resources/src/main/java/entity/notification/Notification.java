package entity.notification;

import entity.order.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Order order;
    private String text;
    private NotificationMethod method;
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
