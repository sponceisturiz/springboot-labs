package online.store.broker.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import online.store.broker.message.OrderMessage;
import online.store.services.ActivityService;

@Component
public class WarehouseProducer {
    private static final Logger log = LoggerFactory.getLogger(WarehouseProducer.class);

    private String WAREHOUSE_TOPIC = "";
    
    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    @Autowired
    private ActivityService activityService;

    public WarehouseProducer(@Value("${messaging.topics.order}") String warehouseTopic){
        WAREHOUSE_TOPIC = warehouseTopic;
    }

    public void publish(OrderMessage orderMessage){
        kafkaTemplate.send(WAREHOUSE_TOPIC, orderMessage);
        log.info("Order placed to warehouse: {}", orderMessage);
        activityService.registerActivity("t-online-orders", "SENT", null);
    }
}
