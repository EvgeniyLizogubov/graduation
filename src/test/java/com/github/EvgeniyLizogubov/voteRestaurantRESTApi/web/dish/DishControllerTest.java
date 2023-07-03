//package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.dish;
//
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.JsonUtil;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.restaurant.RestaurantTestData;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user.UserTestData;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.Dish;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.DishRepository;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.AbstractControllerTest;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class DishControllerTest extends AbstractControllerTest {
//
//    private static final String ADMIN_TEST_URL = DishController.ADMIN_REST_URL.replace("{restaurantId}", RestaurantTestData.restaurant1.getId().toString());
//    private static final String ADMIN_TEST_URL_SLASH = ADMIN_TEST_URL + "/";
//    private static final String PROFILE_TEST_URL = DishController.PROFILE_REST_URL.replace("{restaurantId}", RestaurantTestData.restaurant2.getId().toString());;
//    private static final String PROFILE_TEST_URL_SLASH = PROFILE_TEST_URL + "/";;
//
//    @Autowired
//    private DishRepository dishRepository;
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_TEST_URL))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.dish1, DishTestData.dish2));
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_TEST_URL_SLASH + "1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.dish1));
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(PROFILE_TEST_URL_SLASH + UserTestData.NOT_FOUND))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(ADMIN_TEST_URL_SLASH + DishTestData.dish1.getId()))
//                .andExpect(status().isNoContent());
//        assertFalse(dishRepository.findById(DishTestData.dish1.getId()).isPresent());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void deleteNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.delete(ADMIN_TEST_URL_SLASH + UserTestData.NOT_FOUND))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void create() throws Exception {
//        Dish newDish = DishTestData.getNew();
//        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_TEST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newDish)))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        Dish created = DishTestData.DISH_MATCHER.readFromJson(action);
//        int newId = created.getId();
//        newDish.setId(newId);
//        DishTestData.DISH_MATCHER.assertMatch(created, newDish);
//        DishTestData.DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), newDish);
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void createInvalid() throws Exception {
//        Dish invalid = new Dish(null, null, -1);
//        perform(MockMvcRequestBuilders.post(ADMIN_TEST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void update() throws Exception {
//        Dish updated = DishTestData.getUpdated();
//        perform(MockMvcRequestBuilders.put(ADMIN_TEST_URL_SLASH + DishTestData.dish1.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        DishTestData.DISH_MATCHER.assertMatch(dishRepository.getExisted(DishTestData.dish1.getId()), updated);
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void updateInvalid() throws Exception {
//        Dish invalid = new Dish(DishTestData.dish1);
//        invalid.setName("");
//        perform(MockMvcRequestBuilders.put(ADMIN_TEST_URL_SLASH + DishTestData.dish1.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
//    void updateHtmlUnsafe() throws Exception {
//        Dish unsafe = new Dish(DishTestData.dish1);
//        unsafe.setName("<script>alert(123)</script>");
//        perform(MockMvcRequestBuilders.put(ADMIN_TEST_URL_SLASH + DishTestData.dish1.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(unsafe)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//}
