package ru.victor.kafka_orders.Service;

public interface ModelService <T> {
    void save(T o);
    T get(String id);
}
