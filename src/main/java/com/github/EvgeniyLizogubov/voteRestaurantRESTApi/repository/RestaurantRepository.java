package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("select r from Restaurant r join Dish d on r.id=d.restaurant.id where d.voteDate=?1")
    List<Restaurant> findAllByVoteDate(LocalDate voteDate);
}
