package entities;

import java.math.BigDecimal;

public class Order {
    private String id;
    private BigDecimal total;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }
}
