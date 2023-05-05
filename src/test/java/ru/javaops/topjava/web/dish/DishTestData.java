package ru.javaops.topjava.web.dish;

import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.web.MatcherFactory;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final Dish dish1 = new Dish(1, "Doner", 666);
    public static final Dish dish2 = new Dish(2, "Pita", 300);

    public static Dish getNew() {
        return new Dish(null, "New dish", 125);
    }

    public static Dish getUpdated() {
        return new Dish(dish1.getId(), "Updated", 123);
    }
}
