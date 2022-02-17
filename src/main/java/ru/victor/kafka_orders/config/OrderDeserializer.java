package ru.victor.kafka_orders.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import ru.victor.kafka_orders.models.Client;
import ru.victor.kafka_orders.models.Goods;
import ru.victor.kafka_orders.models.Order;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class OrderDeserializer implements Deserializer<Order> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Order deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Order");
        }
    }
}
