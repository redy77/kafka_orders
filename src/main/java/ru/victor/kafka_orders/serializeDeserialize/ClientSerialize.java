package ru.victor.kafka_orders.serializeDeserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Client;

public class ClientSerialize implements Serializer<Client> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Client client) {
        try {
            if (client == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(client);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Client to byte[]");
        }
    }
}
