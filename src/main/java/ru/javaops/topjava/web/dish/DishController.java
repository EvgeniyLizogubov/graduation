package ru.javaops.topjava.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.repository.DishRepository;
import ru.javaops.topjava.repository.RestaurantRepository;

import java.util.List;

import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {

    static final String ADMIN_REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    static final String PROFILE_REST_URL = "/api/profile/restaurants/{restaurantId}/dishes";

    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping(PROFILE_REST_URL)
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant id={}", restaurantId);
        Restaurant restaurant = restaurantRepository.getWithDishes(restaurantId)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + restaurantId + " not found"));
        return restaurant.getDishes();
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
        restaurantRepository.getExisted(restaurantId).getDishes().add(dish);
        return dishRepository.save(dish);
    }

    @Transactional
    @PutMapping(value = ADMIN_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update {} id={} for restaurant id={}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.save(dish);
    }
}
