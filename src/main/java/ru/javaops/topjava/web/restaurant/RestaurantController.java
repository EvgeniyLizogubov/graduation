package ru.javaops.topjava.web.restaurant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.repository.UserRepository;
import ru.javaops.topjava.web.AuthUser;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava.util.DateTimeUtil.checkVoteDate;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {

    public static final String ADMIN_REST_URL = "/api/admin/restaurants";
    public static final String PROFILE_REST_URL = "/api/profile/restaurants";

    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected UserRepository userRepository;

    @GetMapping(ADMIN_REST_URL)
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.DESC, "voteDate"));
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

    @Transactional
    @GetMapping(PROFILE_REST_URL + "/{id}/vote")
    public Restaurant vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("vote for restaurant id={}", id);
        User user = userRepository.getWithRestaurants(authUser.id()).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        Restaurant voteRestaurant = restaurantRepository.getExisted(id);
        LocalDate voteDate = voteRestaurant.getVoteDate();
        if (checkVoteDate(voteDate)) {
            user.getRestaurants().merge(voteDate, voteRestaurant, (k, v) -> voteRestaurant);
            userRepository.save(user);
            return restaurantRepository.getWithUsers(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        }
        return null;
    }

    @GetMapping(PROFILE_REST_URL + "/{id}/with-dishes")
    public Restaurant getWithDishes(@PathVariable int id) {
        log.info("get restaurant id={} with dishes", id);
        return restaurantRepository.getWithDishes(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }

    @GetMapping(ADMIN_REST_URL + "/{id}/with-users")
    public Restaurant getWithUsers(@PathVariable int id) {
        log.info("get restaurant id={} with users", id);
        return restaurantRepository.getWithUsers(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }

    @DeleteMapping(ADMIN_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
