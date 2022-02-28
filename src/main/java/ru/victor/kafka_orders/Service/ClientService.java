package ru.victor.kafka_orders.Service;

import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.event.KafkaConsumerService;
import ru.victor.kafka_orders.event.KafkaProducerService;
import ru.victor.kafka_orders.models.Client;


@Service
public class ClientService implements ModelService<Client>{

    KafkaConsumerService consumerService;
    KafkaProducerService producerService;

    public ClientService(KafkaConsumerService consumerService, KafkaProducerService producerService) {
        this.consumerService = consumerService;
        this.producerService = producerService;
    }

    @Override
    public void save(Client client) {
        producerService.sendClientToKafka("Clients", client);
    }

    @Override
    public Client get(String name) {
        return consumerService.getClients().get(name);
    }
}
