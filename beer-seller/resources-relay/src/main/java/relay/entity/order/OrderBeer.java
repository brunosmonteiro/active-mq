package relay.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import relay.entity.beer.Beer;
import shared.constants.OrderBeerStatus;

@Entity
public class OrderBeer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "beer_id")
    private Beer beer;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private OrderBeerStatus status;

    public OrderBeer() {
    }

    public OrderBeer(final Beer beer, final Integer quantity, final OrderBeerStatus status) {
        this.beer = beer;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(final Beer beer) {
        this.beer = beer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public OrderBeerStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderBeerStatus status) {
        this.status = status;
    }
}
