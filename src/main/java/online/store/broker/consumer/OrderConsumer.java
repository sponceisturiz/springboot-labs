package online.store.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import online.store.broker.message.OrderMessage;
import online.store.services.ActivityService;

@Component
@KafkaListener(topics = "#{'${messaging.topics.order}'.split(',')}")
public class OrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    ActivityService activityService;
    
    @KafkaHandler
    public void listen(OrderMessage orderMessage){
        log.info("Received order from warehouse: {}", orderMessage); 
        activityService.registerActivity("t-online-orders", "RECEIVED", null);  
    }
}
