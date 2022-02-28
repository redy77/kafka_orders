package ru.victor.kafka_orders.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.victor.kafka_orders.Service.ModelService;
import ru.victor.kafka_orders.models.Client;

@RestController
public class Controllers {

    private final ModelService<Client> clientService;

    public Controllers(ModelService<Client> clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/{name}")
    public ResponseEntity<Client> getClient(@PathVariable String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.get(name));
    }


    @PostMapping("/client")
    public ResponseEntity<Client> newClient(@RequestBody Client client) {
        clientService.save(client);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.get(client.getName()));
    }
}
