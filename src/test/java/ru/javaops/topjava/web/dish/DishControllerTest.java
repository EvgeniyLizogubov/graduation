package ru.javaops.topjava.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.repository.DishRepository;
import ru.javaops.topjava.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.util.JsonUtil.writeValue;
import static ru.javaops.topjava.web.dish.DishController.REST_URL;
import static ru.javaops.topjava.web.dish.DishTestData.*;
import static ru.javaops.topjava.web.restaurant.RestaurantTestData.restaurant1;
import static ru.javaops.topjava.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava.web.user.UserTestData.NOT_FOUND;

public class DishControllerTest extends AbstractControllerTest {

    private static final String TEST_URL = REST_URL.replace("{restaurantId}", restaurant1.getId().toString());
    private static final String TEST_URL_SLASH = TEST_URL + "/";

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL_SLASH + "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(TEST_URL_SLASH + dish1.getId()))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(dish1.getId()).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(TEST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isOk());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, -1);
        perform(MockMvcRequestBuilders.post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(TEST_URL_SLASH + dish1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.getExisted(dish1.getId()), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(dish1);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(TEST_URL_SLASH + dish1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish unsafe = new Dish(dish1);
        unsafe.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(TEST_URL_SLASH + dish1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(unsafe)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
