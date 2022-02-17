package ru.victor.kafka_orders.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.models.Order;
import java.util.Set;

@Service
public class KafkaConsumerService {

    private Set<Order> orders;

    public KafkaConsumerService(Set<Order> orders) {
        this.orders = orders;
    }


    @KafkaListener(topics = "order", groupId = "order", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listenOrder(@Payload Order order) {
        System.out.println("Order by client " + order.getClient().getName() + " was reserved");
        orders.add(order);
    }
    public Set<Order> getOrders() {
        return orders;
    }
}
