//package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user;
//
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.JsonUtil;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Role;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.MatcherFactory;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant.RestaurantTestData.restaurant1;
//import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant.RestaurantTestData.restaurant2;
//
//public class UserTestData {
//    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "restaurants", "password");
//    public static MatcherFactory.Matcher<User> USER_WITH_RESRAURANTS_MATCHER =
//            MatcherFactory.usingAssertions(User.class,
//                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
//                    (a, e) -> assertThat(a).usingRecursiveComparison()
//                            .ignoringFields("restaurants.2024-01-30.users", "restaurants.2024-01-31.dishes", "password").isEqualTo(e),
//                    (a, e) -> {
//                        throw new UnsupportedOperationException();
//                    });
//
//    public static final int USER_ID = 1;
//    public static final int ADMIN_ID = 2;
//    public static final int GUEST_ID = 3;
//    public static final int NOT_FOUND = 100;
//    public static final String USER_MAIL = "user@yandex.ru";
//    public static final String ADMIN_MAIL = "admin@gmail.com";
//    public static final String GUEST_MAIL = "guest@gmail.com";
//
//    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", null, Role.USER);
//    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", null, Role.ADMIN, Role.USER);
//    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest", null);
//
//    static {
//        user.setRestaurants(Map.of(restaurant1.getVoteDate(), restaurant1, restaurant2.getVoteDate(), restaurant2));
//        admin.setRestaurants(Map.of(restaurant2.getVoteDate(), restaurant2));
//    }
//
//    public static User getNew() {
//        return new User(null, "New", "new@gmail.com", "newPass", true,null, Collections.singleton(Role.USER));
//    }
//
//    public static User getUpdated() {
//        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", true,null, List.of(Role.ADMIN));
//    }
//
//    public static String jsonWithPassword(User user, String passw) {
//        return JsonUtil.writeAdditionProps(user, "password", passw);
//    }
//}
