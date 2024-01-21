package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.dish;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Dish;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.DishRepository;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.RestaurantRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {

    static final String ADMIN_REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    static final String PROFILE_REST_URL = "/api/profile/restaurants/{restaurantId}/dishes";

    protected DishRepository dishRepository;
    protected final RestaurantRepository restaurantRepository;

    @GetMapping(PROFILE_REST_URL + "/vote-date/{voteDate}")
    public List<Dish> getAllByRestaurantAndVoteDate(@PathVariable int restaurantId,
                                           @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate) {
        log.info("get all dishes for restaurant id={} by voteDate={}", restaurantId, voteDate);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        return dishRepository.findByRestaurantAndVoteDateOrderByName(restaurant, voteDate);
    }

    @GetMapping(PROFILE_REST_URL + "/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish id={} for restaurant id={}", id, restaurantId);
        return dishRepository.getExisted(id);
    }

    @Transactional
    @DeleteMapping(ADMIN_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish id={} for restaurant id={}", id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @Transactional
    @PostMapping(value = ADMIN_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        log.info("create new dish {} for restaurant id={}", dish, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    @Transactional
    @PutMapping(value = ADMIN_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update {} id={} for restaurant id={}", dish, id, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        assureIdConsistent(dish, id);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }
}
