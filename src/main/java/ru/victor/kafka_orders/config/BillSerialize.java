package ru.victor.kafka_orders.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.victor.kafka_orders.models.Bill;
import ru.victor.kafka_orders.models.Order;

public class BillSerialize implements Serializer<Bill> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Bill bill) {
        try {
            if (bill == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(bill);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Bill to byte[]");
        }
    }
}
