package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.HasId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {
}
