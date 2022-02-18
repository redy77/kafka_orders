package ru.victor.kafka_orders.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.Service.Calculate;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Order;
import java.util.Set;

@Service
public class KafkaConsumerService {

    private final KafkaProducerService producerService;
    private final Calculate bill;
    private final Set<Order> orders;

    @Autowired
    public KafkaConsumerService(KafkaProducerService producerService, Calculate orderAmount, Set<Order> orders) {
        this.producerService = producerService;
        this.bill = orderAmount;
        this.orders = orders;
    }

    @KafkaListener(topics = "New_Order", groupId = "order", containerFactory = "concurrentKafkaListenerContainerFactoryBill")
    public void listenOrder(@Payload Order order) {
        System.out.println("Order by client " + order.getClient().getName() + " was reserved");
        orders.add(order);
        producerService.sendBillToKafka("Order_to_Pay", new Bill(order.getClient(), bill.clientBill(order)));
        System.out.println(bill.clientBill(order).toString());
    }

    @KafkaListener(topics = "Order_to_Pay", groupId = "order")
    public void listenOrder(@Payload String bill) {
        System.out.println("Order by client " + bill + " was reserved");
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
