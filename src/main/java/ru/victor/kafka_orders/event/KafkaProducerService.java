package ru.victor.kafka_orders.event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Order;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplateOrder;
    private final KafkaTemplate<String, Bill> kafkaTemplateBill;


    public KafkaProducerService(@Qualifier("kafkaTemplateOrders") KafkaTemplate<String, Order> kafkaTemplate, @Qualifier("kafkaTemplateBill") KafkaTemplate<String, Bill> kafkaTemplateBill) {
        this.kafkaTemplateOrder = kafkaTemplate;
        this.kafkaTemplateBill = kafkaTemplateBill;
    }

    public void sendBillToKafka(String topic, Bill bill) {

        ListenableFuture<SendResult<String, Bill>> future = kafkaTemplateBill.send(topic, bill);
        future.addCallback(new ListenableFutureCallback<>(){

            @Override
            public void onSuccess(SendResult<String, Bill> result) {
                System.out.println("Message was sent to topic: " + result.getRecordMetadata().topic() +
                        ", with offset: " + result.getRecordMetadata().offset() +
                        ", at: " + result.getRecordMetadata().timestamp() + "bill" + result.getProducerRecord().value());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message: " + ex);
            }
        });
    }


    public void sendOrderToKafka(String topic, Order order) {

        ListenableFuture<SendResult<String, Order>> future = kafkaTemplateOrder.send(topic, order);
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
