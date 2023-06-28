package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Dish;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

}
