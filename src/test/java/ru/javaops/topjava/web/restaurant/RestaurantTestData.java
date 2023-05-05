package ru.javaops.topjava.web.restaurant;

import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava.web.dish.DishTestData.dish1;
import static ru.javaops.topjava.web.dish.DishTestData.dish2;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("users").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final Restaurant restaurant1 = new Restaurant(1, "Kebab", LocalDate.of(2020, Month.JANUARY, 30), null, null);
    public static final Restaurant restaurant2 = new Restaurant(2, "McDonalds", LocalDate.of(2020, Month.JANUARY, 30), null, null);

    static {
        restaurant1.setDishes(List.of(dish1, dish2));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Burger King", LocalDate.of(2020, Month.JANUARY, 31), null, null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(restaurant1.getId(), "Burger King", LocalDate.of(2020, Month.JANUARY, 31), null, null);
    }
}
