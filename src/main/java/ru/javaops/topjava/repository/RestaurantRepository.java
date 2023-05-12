package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes WHERE r.id=?1")
    Optional<Restaurant> getWithDishes(int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.users WHERE r.id=?1")
    Optional<Restaurant> getWithUsers(int id);

    List<Restaurant> findAllByVoteDate(LocalDate voteDate);
}
