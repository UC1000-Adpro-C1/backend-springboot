package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name="status")
    private String status;

    @Column(name = "buyer_id", nullable = false)
    private String buyerId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    public Order() {}

    public Order(Order.OrderBuilder builder) {
        this.id = UUID.randomUUID().toString();
        this.status = builder.status;
        this.buyerId = builder.buyerId;
        this.productName = builder.productName;
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static class OrderBuilder {
        // Required parameters
        private String buyerId;
        private String productName;

        // Optional parameters
        private String status;

        public OrderBuilder(String buyerId, String productName) {
            this.buyerId = buyerId;
            this.productName = productName;
            this.status = OrderStatus.WAITING_PAYMENT.getValue();
        }

        public Order.OrderBuilder setBuyerId(String buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Order.OrderBuilder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
