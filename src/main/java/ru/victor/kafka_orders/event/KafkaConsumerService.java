package ru.victor.kafka_orders.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.Service.Calculate;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Client;
import ru.victor.kafka_orders.models.Order;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;


@Service
public class KafkaConsumerService {

    private final KafkaProducerService producerService;
    private final Calculate bill;
    private final HashMap<String, Order> orders;
    private final HashMap<String, Bill> bills;
    private final HashMap<String, Client> clients;

    @Autowired
    public KafkaConsumerService(KafkaProducerService producerService, Calculate orderAmount, HashMap<String, Order> orders, HashMap<String, Bill> bills, HashMap<String, Client> clients) {
        this.producerService = producerService;
        this.bill = orderAmount;
        this.orders = orders;
        this.bills = bills;
        this.clients = clients;
    }

    @KafkaListener(topics = "Clients", containerFactory = "concurrentKafkaListenerContainerFactoryClient")
    public void listenClient(@Payload Client client) {
        System.out.println("Client was reserved " + client.getName() + " was reserved");
        clients.put(client.getName(), client);
    }

    @KafkaListener(topics = "New_Order", containerFactory = "concurrentKafkaListenerContainerFactoryOrder")
    public void listenOrder(@Payload Order order) {
        System.out.println("Order by client " + order.getClient().getName() + " was reserved");
        orders.put(order.getClient().getName(), order);
        producerService.sendBillToKafka("Order_to_Pay", new Bill(order.getClient(), bill.clientBill(order)));
    }

    @KafkaListener(topics = "Order_to_Pay", containerFactory = "concurrentKafkaListenerContainerFactoryBill")
    public void listenOrder(@Payload Bill bill) {
        System.out.println("Order by client " + bill.getClient().getName() + " was reserved");
        bills.put(bill.getClient().getName(), bill);
    }

    public HashMap<String, Order> getOrders() {
        return orders;
    }

    public HashMap<String, Bill> getBills() {
        return bills;
    }

    public HashMap<String, Client> getClients() {
        return clients;
    }
}
