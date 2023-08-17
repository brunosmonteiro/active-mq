package shared.entity.order;

import jakarta.persistence.CascadeType;
import shared.entity.notification.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderBeer> beers;
    @OneToMany(mappedBy = "order")
    private List<Notification> notifications;
    private String consumerId;
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<OrderBeer> getBeers() {
        return beers;
    }

    public void setBeers(final List<OrderBeer> beers) {
        this.beers = beers;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }
}
