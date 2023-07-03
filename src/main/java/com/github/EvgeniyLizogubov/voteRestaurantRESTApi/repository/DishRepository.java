package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Dish;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findByRestaurantAndVoteDateOrderByName(Restaurant restaurant, LocalDate voteDate);
}
