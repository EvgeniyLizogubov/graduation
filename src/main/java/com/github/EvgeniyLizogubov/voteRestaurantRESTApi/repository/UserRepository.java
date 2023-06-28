package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.config.SecurityConfig;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.error.NotFoundException;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    //    https://stackoverflow.com/a/46013654/548473
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.restaurants WHERE u.id=?1")
    Optional<User> getWithRestaurants(int id);

    @Transactional
    default User prepareAndSave(User user) {
        user.setPassword(SecurityConfig.PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}