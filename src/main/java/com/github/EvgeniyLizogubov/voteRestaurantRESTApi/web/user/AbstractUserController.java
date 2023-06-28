package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.error.NotFoundException;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.UserRepository;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public User getWithRestaurants(int id) {
        log.info("getWithRestaurants {}", id);
        return repository.getWithRestaurants(id).orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
    }
}