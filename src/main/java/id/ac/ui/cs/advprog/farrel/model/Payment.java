package id.ac.ui.cs.advprog.farrel.model;

import java.util.UUID;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name="payment")
@Entity
@Getter
public class Payment {

    @Id
    @Column(name = "paymentId", updatable = false, nullable = false)
    private UUID id;

    @Column(name="amount")
    private long amount;

    @Column(name = "userOwnerId", updatable = false, nullable = false)
    private String userId;

    @Column(name = "status")
    private String status;

    @Column(name = "handledBy")
    private String handledBy;

    private Payment(PaymentBuilder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.userId = builder.userId;
        this.status = builder.status;
        this.handledBy = builder.handledBy;
    }

    // Getters for the fields

    public static class PaymentBuilder {
        private UUID id;
        private long amount;
        private String userId;
        private String status;
        private String handledBy;

        public PaymentBuilder(UUID id, long amount, String userId) {
            this.id = id;
            this.amount = amount;
            this.userId = userId;
            this.status = PaymentStatus.PENDING.getValue();
        }

        public PaymentBuilder handledBy(String handledBy) {
            this.handledBy = handledBy;
            return this;
        }

        public PaymentBuilder status(String status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder setStatus(String newStatus) {
            this.status = newStatus;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}