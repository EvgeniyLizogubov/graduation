package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
public class Vote extends BaseEntity {
    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @OneToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @NotNull
    private Restaurant chosenRestaurant;
}
