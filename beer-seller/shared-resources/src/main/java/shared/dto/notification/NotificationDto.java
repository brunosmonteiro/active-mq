package shared.dto.notification;

public class NotificationDto {
    private String text;
    private Long consumerId;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final Long consumerId) {
        this.consumerId = consumerId;
    }
}
