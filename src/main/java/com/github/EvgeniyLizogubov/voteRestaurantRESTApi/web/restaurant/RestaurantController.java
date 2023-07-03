package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.RestaurantRepository;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    public static final String ADMIN_REST_URL = "/api/admin/restaurants";
    public static final String PROFILE_REST_URL = "/api/profile/restaurants";

    protected RestaurantRepository restaurantRepository;
    protected UserRepository userRepository;

    @GetMapping(ADMIN_REST_URL)
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping(PROFILE_REST_URL + "/vote-date/{voteDate}")
    public List<Restaurant> getAllByVoteDate(
            @PathVariable @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate
    ) {
        log.info("get all restaurants by voteDate");
        return restaurantRepository.findAllByVoteDate(voteDate);
    }

    @GetMapping(PROFILE_REST_URL + "/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return restaurantRepository.getExisted(id);
    }

    @DeleteMapping(ADMIN_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("remote restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PostMapping(value = ADMIN_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Restaurant create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create new {}", restaurant);
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @PutMapping(value = ADMIN_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
