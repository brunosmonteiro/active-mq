package relay.entity.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import relay.entity.notification.Notification;
import shared.constants.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderBeer> beers = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<Notification> notifications;
    private String consumerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal total;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public void addOrderBeer(final OrderBeer orderBeer) {
        this.beers.add(orderBeer);
        orderBeer.setOrder(this);
    }
}
