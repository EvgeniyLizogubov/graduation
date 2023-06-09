package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util;

import lombok.experimental.UtilityClass;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Role;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.to.UserTo;

@UtilityClass
public class UsersUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), null,Role.USER);
//-        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), userTo.getCaloriesPerDay(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
//-        user.setCaloriesPerDay(userTo.getCaloriesPerDay());
        user.setPassword(userTo.getPassword());
        return user;
    }
}