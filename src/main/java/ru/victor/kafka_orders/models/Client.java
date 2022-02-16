package ru.victor.kafka_orders.models;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private String name;
    private double discount;

    public Client(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

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

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }
}
