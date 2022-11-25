package online.store.broker.message;

public class PaymentReplyMessage {
    public boolean paymentAccomplished;
    public String uuid;
    public String cause;

    public PaymentReplyMessage() {
    }
    public PaymentReplyMessage(boolean paymentAccomplished, String uuid, String cause) {
        this.paymentAccomplished = paymentAccomplished;
        this.uuid = uuid;
        this.cause = cause;
    }
    public boolean isPaymentAccomplished() {
        return paymentAccomplished;
    }
    public void setPaymentAccomplished(boolean paymentAccomplished) {
        this.paymentAccomplished = paymentAccomplished;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    @Override
    public String toString() {
        return "PaymentReplyMessage [paymentAccomplished=" + paymentAccomplished + ", cause=" + cause + "]";
    }
}
