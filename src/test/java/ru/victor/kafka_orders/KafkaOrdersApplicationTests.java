package ru.victor.kafka_orders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import ru.victor.kafka_orders.Service.Calculate;
import ru.victor.kafka_orders.event.KafkaConsumerService;
import ru.victor.kafka_orders.event.KafkaProducerService;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Client;
import ru.victor.kafka_orders.models.Goods;
import ru.victor.kafka_orders.models.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
class KafkaOrdersApplicationTests {

    private static Order order;
    private static Client client;
    private static Bill bill;
    private static Bill billNegative;

    @Autowired
    private Calculate calculate;

    @Autowired
    private KafkaConsumerService kafkaConsumer;

    @Autowired
    private KafkaProducerService kafkaProducer;

    @BeforeAll
    static void objectsForSend() {
        client = new Client( "Ivan", 0.15);
        Goods good1 = new Goods( "Chips", BigDecimal.valueOf(2.5));
        Goods good2 = new Goods( "Bear", BigDecimal.valueOf(3.5));
        Goods good3 = new Goods( "Girls", BigDecimal.valueOf(15.5));
        List<Goods> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        order = Order.builder().goods(goods).client(client).build();
        bill = new Bill(client, BigDecimal.valueOf(3.225));
        billNegative = new Bill( new Client("Dmitriy", 1.15), BigDecimal.valueOf(4.225));
    }

    @BeforeTestClass
    public void sendKafka() throws InterruptedException {
        kafkaProducer.sendOrderToKafka("New_Order", order);
        kafkaProducer.sendClientToKafka("Clients", client);
        Thread.sleep(1000);
    }

    @Test
    void sendAndReserveOrderTest() throws InterruptedException {
        sendKafka();
        sendKafka();
        kafkaConsumer.getOrders().forEach(s -> Assertions.assertEquals("Ivan", s.getClient().getName()));
        Assertions.assertEquals(2, kafkaConsumer.getOrders().size());
    }

    @Test
    void sendAndReserveClientTest() throws InterruptedException {
        sendKafka();
        System.out.println(kafkaConsumer.getClients());
        Assertions.assertEquals("Ivan", kafkaConsumer.getClients().getLast().getName());
    }

    @Test
    void calculateClassTest() throws InterruptedException {
        sendKafka();
        kafkaConsumer.getOrders().forEach(s -> Assertions.assertEquals(BigDecimal.valueOf(3.225), calculate.clientBill(s)));
    }

    @Test
    void calculateClassNegativeTest() throws InterruptedException {
        sendKafka();
        kafkaConsumer.getOrders().forEach(s -> Assertions.assertNotEquals(BigDecimal.valueOf(4.225), calculate.clientBill(s)));
    }

    @Test
    void sendAndReserveBillTest() {
        kafkaConsumer.getBills().forEach(s -> Assertions.assertEquals(bill, s));
    }

    @Test
    void sendAndReserveBillNegativeTest() {
        kafkaConsumer.getBills().forEach(s -> Assertions.assertNotEquals(billNegative, s));
    }
}
