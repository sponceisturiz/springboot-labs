package online.store.broker.message;

import java.math.BigDecimal;

public class PaymentMessage {

    private String uuid;
    private String firstName;
    private String lastName;
    private String creditCard;
    private BigDecimal totalAmount;

    public PaymentMessage() {
    }
    
    public PaymentMessage(String uuid, String firstName, String lastName, String creditCard, BigDecimal totalAmount) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.totalAmount = totalAmount;
    }


    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getCreditCard() {
        return creditCard;
    }
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PaymentMessage [firstName=" + firstName + ", lastName=" + lastName + ", creditCard=" + creditCard
                + ", totalAmount=" + totalAmount + "]";
    }   
}
