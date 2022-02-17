package ru.victor.kafka_orders.config.Service;

import org.springframework.stereotype.Service;
import ru.victor.kafka_orders.models.Client;
import ru.victor.kafka_orders.models.Order;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderAmount {
    public BigDecimal orderAmount(Order order){
        double discount = order.getClient().getDiscount();
        Optional<BigDecimal> amount = order.getGoods().stream()
                .map(s -> s.getPrice().multiply(BigDecimal.valueOf(discount)))
                .reduce(BigDecimal::add);
        return (amount.isPresent())? amount.get() : BigDecimal.valueOf(0);
    }
}
