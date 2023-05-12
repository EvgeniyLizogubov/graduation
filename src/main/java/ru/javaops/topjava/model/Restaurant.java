package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava.HasId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "vote_date"}, name = "restaurant_unique_name_votedate_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate voteDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("name ASC")
    private List<Dish> dishes;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
    @JsonIgnoreProperties({"restaurants"})
    private List<User> users;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.voteDate, r.dishes, r.users);
    }

    public Restaurant(Integer id, String name, LocalDate voteDate, List<Dish> dishes, List<User> users) {
        super(id, name);
        this.voteDate = voteDate;
        this.dishes = dishes;
        this.users = users;
    }
}
