package ru.javaops.topjava.web.restaurant;

import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int NOT_FOUND = 1000;

    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final Restaurant restaurant1 = new Restaurant(1, "McDonalds", LocalDate.of(2020, Month.JANUARY, 30), null, null);
    public static final Restaurant restaurant2 = new Restaurant(2, "KFC", LocalDate.of(2020, Month.JANUARY, 30), null, null);

    public static Restaurant getNew() {
        return new Restaurant(null, "Burger King", LocalDate.of(2020, Month.JANUARY, 31), null, null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(restaurant1.getId(), "Burger King", LocalDate.of(2020, Month.JANUARY, 31), null, null);
    }
}
