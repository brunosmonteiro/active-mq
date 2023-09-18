package shared.dto.notification;

import shared.constants.notification.NotificationMethod;
import shared.constants.notification.NotificationStatus;

public class NotificationCreationDto {
    private Long orderId;
    private String text;
    private NotificationMethod method;
    private NotificationStatus status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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
