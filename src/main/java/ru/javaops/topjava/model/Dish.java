package ru.javaops.topjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Min(1)
    private int price;

    public Dish(Dish d) {
        this(d.id, d.name, d.price);
    }

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
