package ru.victor.kafka_orders.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.victor.kafka_orders.event.KafkaConsumerService;
import ru.victor.kafka_orders.event.KafkaProducerService;
import ru.victor.kafka_orders.models.Client;

@RestController
public class Controllers {
    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;

    public Controllers(KafkaProducerService producerService, KafkaConsumerService consumerService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
    }
    @GetMapping("/client/{name}")
    public ResponseEntity<Client> getClient(@PathVariable String name){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(consumerService.getClients().get(name));
    }


    @PostMapping("/client")
    public ResponseEntity<Client> newClient(@RequestBody Client client){
        producerService.sendClientToKafka("Clients", client);
        Client newClient = consumerService.getClients().get(client.getName());
        if(!client.equals(newClient)) return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newClient);
    }
}
