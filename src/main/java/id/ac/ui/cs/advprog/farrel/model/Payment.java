package id.ac.ui.cs.advprog.farrel.model;

public class Payment {
    private String id;
    private long amount;
    private String userId;
    private boolean status;
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
        private String id;
        private long amount;
        private String userId;
        private boolean status;
        private String handledBy;

        public PaymentBuilder(String id, long amount, String userId) {
            this.id = id;
            this.amount = amount;
            this.userId = userId;
        }

        public PaymentBuilder status(boolean status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder handledBy(String handledBy) {
            this.handledBy = handledBy;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}