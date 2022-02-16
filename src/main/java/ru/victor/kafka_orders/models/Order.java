package ru.victor.kafka_orders.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private Client client;
    private List<Goods> goods;

    public Order() {
    }

    public Order(Client client, List<Goods> goods) {
        this.client = client;
        this.goods = goods;
    }

    public Client getClient() {
        return client;
    }

    public List<Goods> getGoods() {
        return goods;
    }

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
