package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
    private Integer price;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.price);
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
