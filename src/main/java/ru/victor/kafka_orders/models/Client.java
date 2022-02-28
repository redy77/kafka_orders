package ru.victor.kafka_orders.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {
    private String name;
    private double discount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.discount, discount) == 0 && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, discount);
    }
}
