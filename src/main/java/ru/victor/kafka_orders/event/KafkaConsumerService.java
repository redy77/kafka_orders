package ru.victor.kafka_orders.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.Service.Calculate;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Order;

import java.util.List;
import java.util.Set;

@Service
public class KafkaConsumerService {

    private final KafkaProducerService producerService;
    private final Calculate bill;
    private final List<Order> orders;
    private final List<Bill> bills;

    @Autowired
    public KafkaConsumerService(KafkaProducerService producerService, Calculate orderAmount, List<Order> orders, List<Bill> bills) {
        this.producerService = producerService;
        this.bill = orderAmount;
        this.orders = orders;
        this.bills = bills;
    }

    @KafkaListener(topics = "New_Order", containerFactory = "concurrentKafkaListenerContainerFactoryOrder")
    public void listenOrder(@Payload Order order) {
        System.out.println("Order by client " + order.getClient().getName() + " was reserved");
        orders.add(order);
        producerService.sendBillToKafka("Order_to_Pay", new Bill(order.getClient(), bill.clientBill(order)));
    }

    @KafkaListener(topics = "Order_to_Pay", containerFactory = "concurrentKafkaListenerContainerFactoryBill")
    public void listenOrder(@Payload Bill bill) {
        System.out.println("Order by client " + bill.getClient().getName() + " was reserved");
        bills.add(bill);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Bill> getBills() {
        return bills;
    }
}
