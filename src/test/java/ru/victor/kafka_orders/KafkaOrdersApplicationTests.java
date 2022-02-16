package ru.victor.kafka_orders;

import kafka.KafkaTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.victor.kafka_orders.event.KafkaConsumer;
import ru.victor.kafka_orders.event.KafkaProducer;
import ru.victor.kafka_orders.models.Client;
import ru.victor.kafka_orders.models.Goods;
import ru.victor.kafka_orders.models.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"} )

class KafkaOrdersApplicationTests {



    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${test.topic}")
    String topic;

    @Test
    void sendAndReserveKafka() {
        Client client = new Client("Ivan", 15.0);
        Goods good1 = new Goods("Cheaps", BigDecimal.valueOf(2.5));
        Goods good2 = new Goods("Bear", BigDecimal.valueOf(3.5));
        Goods good3 = new Goods("Girls", BigDecimal.valueOf(15.5));
        List<Goods> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        Order order = new Order(client, goods);
        Order order1 = new Order();
        kafkaProducer.sendToKafka("order", order);
    }

}
