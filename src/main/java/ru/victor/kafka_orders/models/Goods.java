package ru.victor.kafka_orders.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Goods {
    private String nameGood;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(nameGood, goods.nameGood) && Objects.equals(price, goods.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameGood, price);
    }

}
