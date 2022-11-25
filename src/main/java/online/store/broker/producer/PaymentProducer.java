package online.store.broker.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import online.store.broker.message.PaymentMessage;
import online.store.services.ActivityService;

@Component
public class PaymentProducer {
    private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);

    private String PAYMENT_TOPIC = "";

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private KafkaTemplate<String, PaymentMessage> kafkaTemplate;

    public PaymentProducer(@Value("${messaging.topics.payment}") String paymentTopic){
        PAYMENT_TOPIC = paymentTopic;
    }

    public void publish(PaymentMessage paymentMessage){
        kafkaTemplate.send(PAYMENT_TOPIC, paymentMessage);
        activityService.registerActivity("t-online-payments", "SENT", null);
        log.info("Payment Order sent {}", paymentMessage);
    }
}
