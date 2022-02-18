package ru.victor.kafka_orders.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bill {
    private Client client;
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill1 = (Bill) o;
        return Objects.equals(client, bill1.client) && Objects.equals(amount, bill1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, amount);
    }
}
