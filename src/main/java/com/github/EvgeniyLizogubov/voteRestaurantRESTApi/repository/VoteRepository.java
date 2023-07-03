package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    List<Vote> findByVoteDateAndChosenRestaurant(LocalDate voteDate, Restaurant restaurant);
}
