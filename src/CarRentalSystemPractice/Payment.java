package CarRentalSystemPractice;

public class Payment {
    String id;
    double amount;
    PaymentStatus status;
    User userId;
    String transactionId;

    public Payment(User userId, double amount) {
        this.id = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.transactionId = null;
    }

    void completePayment(String transactionId) {
        this.status = PaymentStatus.COMPLETE;
        this.transactionId = transactionId;
    }

    void failPayment() {
        this.status = PaymentStatus.FAILED;
        this.transactionId = null;
    }

    void refundPayment() {
        if (this.status == PaymentStatus.COMPLETE) {
            this.status = PaymentStatus.REFUNDED;
            this.transactionId = null;
        }
    }
}
