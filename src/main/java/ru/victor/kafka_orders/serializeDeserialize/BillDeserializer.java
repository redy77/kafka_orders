package ru.victor.kafka_orders.serializeDeserialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import ru.victor.kafka_orders.models.Bill;
import java.nio.charset.StandardCharsets;

@Component
public class BillDeserializer implements Deserializer<Bill> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Bill deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Bill");
        }
    }
}
