//package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant;
//
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.JsonUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Restaurant;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.RestaurantRepository;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.AbstractControllerTest;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user.UserTestData;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant.RestaurantTestData.*;
//import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user.UserTestData.ADMIN_MAIL;
//
//public class RestaurantControllerTest extends AbstractControllerTest {
//
//    private static final String PROFILE_REST_URL_SLASH = RestaurantController.PROFILE_REST_URL + "/";
//    private static final String ADMIN_REST_URL_SLASH = RestaurantController.ADMIN_REST_URL + "/";
//
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(RestaurantController.ADMIN_REST_URL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2, restaurant3, restaurant1));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAllByVoteDate() throws Exception {
//        perform(MockMvcRequestBuilders.get(RestaurantController.PROFILE_REST_URL + "/vote-date/2024-01-31"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant3, restaurant2));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL_SLASH + restaurant1.getId()))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL_SLASH + NOT_FOUND))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void vote() throws Exception {
//        Restaurant restaurant = new Restaurant(restaurant1);
//        restaurant.setUsers(List.of(UserTestData.user, UserTestData.admin));
//
//        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL_SLASH + restaurant1.getId() + "/vote"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_WITH_USERS_MATCHER.contentJson(restaurant));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getWithDishes() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL_SLASH + restaurant2.getId() + "/with-dishes"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_WITH_USERS_MATCHER.contentJson(restaurant2));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getWithUsers() throws Exception {
//        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL_SLASH + restaurant1.getId() + "/with-users"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_WITH_USERS_MATCHER.contentJson(restaurant1));
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL_SLASH + restaurant1.getId()))
//                .andExpect(status().isNoContent());
//        assertFalse(restaurantRepository.findById(restaurant1.getId()).isPresent());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void deleteNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL_SLASH + NOT_FOUND))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void create() throws Exception {
//        Restaurant newRestaurant = getNew();
//        ResultActions action = perform(MockMvcRequestBuilders.post(RestaurantController.ADMIN_REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newRestaurant)))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
//        int newId = created.getId();
//        newRestaurant.setId(newId);
//        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
//        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createInvalid() throws Exception {
//        Restaurant invalid = new Restaurant(null, null, null, null, null);
//        perform(MockMvcRequestBuilders.post(RestaurantController.ADMIN_REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void update() throws Exception {
//        Restaurant updated = getUpdated();
//        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL_SLASH + restaurant1.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(restaurant1.getId()), updated);
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void updateInvalid() throws Exception {
//        Restaurant invalid = new Restaurant(restaurant1);
//        invalid.setName("");
//        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL_SLASH + invalid.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void updateHtmlUnsafe() throws Exception {
//        Restaurant unsafe = new Restaurant(restaurant1);
//        unsafe.setName("<script>alert(123)</script>");
//        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL_SLASH + unsafe.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(unsafe)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//}
