package ru.victor.kafka_orders.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order implements Serializable {

    private Client client;
    private List<Goods> goods;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(client, order.client) && Objects.equals(goods, order.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, goods);
    }

}
