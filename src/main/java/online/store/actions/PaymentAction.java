package online.store.actions;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.store.broker.message.PaymentMessage;
import online.store.broker.producer.PaymentProducer;

@Component
public class PaymentAction {

    @Autowired
    PaymentProducer paymentProducer;
    
    public void publishToBroker(String uuid, String firstName, String lastName, String creditCard, BigDecimal totalAmount){
        PaymentMessage paymentMessage = new PaymentMessage(uuid, firstName, lastName, creditCard, totalAmount);
        paymentProducer.publish(paymentMessage);
    }
}
