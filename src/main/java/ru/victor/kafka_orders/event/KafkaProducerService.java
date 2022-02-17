package ru.victor.kafka_orders.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.victor.kafka_orders.models.Order;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;


    public KafkaProducerService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToKafka(String topic, Order order) {

        ListenableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topic, order);
        future.addCallback(new ListenableFutureCallback<>(){

            @Override
            public void onSuccess(SendResult<String, Order> result) {
                System.out.println("Message was sent to topic: " + result.getRecordMetadata().topic() +
                        ", with offset: " + result.getRecordMetadata().offset() +
                        ", at: " + result.getRecordMetadata().timestamp() + " " + result.getProducerRecord().value().getClient().getName());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message: " + ex);
            }
        });
    }
}
