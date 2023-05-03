package ru.javaops.topjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import ru.javaops.topjava.HasId;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "date"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<User> users;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.date, r.dishes, r.users);
    }

    public Restaurant(Integer id, String name, LocalDate date, Collection<Dish> dishes, Collection<User> users) {
        super(id, name);
        this.date = date;
        setDishes(dishes);
        setUsers(users);
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? null : List.copyOf(dishes);
    }

    public void setUsers(Collection<User> users) {
        this.users = CollectionUtils.isEmpty(users) ? null : List.copyOf(users);
    }
}
