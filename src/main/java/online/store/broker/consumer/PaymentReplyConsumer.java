package online.store.broker.consumer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import online.store.broker.message.OrderMessage;
import online.store.broker.message.PaymentReplyMessage;
import online.store.broker.producer.WarehouseProducer;
import online.store.model.Order;
import online.store.services.ActivityService;
import online.store.services.OrdersService;


@Component
@KafkaListener(topics = "#{'${messaging.topics.paymentReply}'.split(',')}")
public class PaymentReplyConsumer {
    private static final Logger log = LoggerFactory.getLogger(PaymentReplyConsumer.class);

    @Autowired
    OrdersService ordersService;

    @Autowired
    ActivityService activityService;

    @Autowired
    WarehouseProducer warehouseProducer;
    
    @KafkaHandler
    public void listen(PaymentReplyMessage paymentReplyMessage){
        log.info("Received reply from Payment Order {}", paymentReplyMessage);
        activityService.registerActivity("t-online-payments-reply", "RECEIVED", null);
        
        if(paymentReplyMessage.isPaymentAccomplished()){
            log.info("Payment Confirmed: {}", paymentReplyMessage);
            activityService.registerActivity("t-online-payments-reply", "CONFIRMED", "Payment");
            List<Order> orders = ordersService.retrieveOrderPlacedByUuId(paymentReplyMessage.getUuid());
            OrderMessage orderMessage = new OrderMessage(orders);

            warehouseProducer.publish(orderMessage);
        }else{
            log.warn("Has occurred an error on the payment, the cause was '{}'", paymentReplyMessage.getCause());
            activityService.registerActivity("t-online-payments-reply", "REJECTED", paymentReplyMessage.getCause());
        }
    }
}
