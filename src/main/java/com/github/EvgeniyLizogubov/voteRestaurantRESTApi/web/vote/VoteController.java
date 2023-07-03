package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.vote;

import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Vote;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.RestaurantRepository;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.UserRepository;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.VoteRepository;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.AuthUser;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.DateTimeUtil.checkVoteDate;
import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    public static final String ADMIN_REST_URL = "/api/admin/votes";
    public static final String PROFILE_REST_URL = "/api/profile/votes";

    protected final VoteRepository voteRepository;
    protected final RestaurantRepository restaurantRepository;
    protected final UserRepository userRepository;

    @GetMapping(ADMIN_REST_URL + "/vote-date/{voteDate}/restaurants/{restaurantId}")
    public List<Vote> getAllByVoteDateAndRestaurant(
            @PathVariable @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate voteDate,
            @PathVariable int restaurantId
            ) {
        log.info("Get all votes by voteDate = {} and restaurantId = {}", voteDate, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        return voteRepository.findByVoteDateAndChosenRestaurant(voteDate, restaurant);
    }

    @GetMapping(PROFILE_REST_URL + "/{id}")
    public Vote get(@PathVariable int id) {
        log.info("Get vote with id = {}", id);
        return voteRepository.getExisted(id);
    }

    @PostMapping(value = PROFILE_REST_URL + "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Vote create(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("create new vote by authUser: {} for restaurant with id = {}", authUser, restaurantId);
        User user = userRepository.getExisted(authUser.id());
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setChosenRestaurant(restaurant);
        vote.setVoteDate(LocalDate.now());
        return voteRepository.save(vote);
    }

    @PutMapping(value = PROFILE_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id,
                       @RequestParam int restaurantId) {
        log.info("update vote with id = {} by authUser: {}", id, authUser);
        User user = userRepository.getExisted(authUser.id());
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        assureIdConsistent(restaurant, restaurantId);

        Vote vote = voteRepository.getExisted(id);
        if (vote.getUser().equals(user) && checkVoteDate(vote.getVoteDate())) {
            vote.setChosenRestaurant(restaurant);
            voteRepository.save(vote);
        }
    }

    @DeleteMapping(value = ADMIN_REST_URL + "/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote with id = {}", id);
        voteRepository.deleteExisted(id);
    }
}
