package ru.javaops.topjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends NamedEntity {

    @JoinColumn(name = "restaurant", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "price", nullable = false)
    @NotNull
    @Min(1)
    private Integer price;
}
