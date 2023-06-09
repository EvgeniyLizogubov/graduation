//package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user;
//
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.JsonUtil;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util.UsersUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.model.User;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.repository.UserRepository;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.to.UserTo;
//import com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.AbstractControllerTest;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static com.github.EvgeniyLizogubov.voteRestaurantRESTApi.web.user.ProfileController.REST_URL;
//
//class ProfileControllerTest extends AbstractControllerTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user));
//    }
//
//    @Test
//    void getUnauthorized() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL))
//                .andExpect(status().isNoContent());
//        UserTestData.USER_MATCHER.assertMatch(userRepository.findAll(), UserTestData.admin, UserTestData.guest);
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void update() throws Exception {
//        UserTo updatedTo = new UserTo(null, "newName", UserTestData.USER_MAIL, "newPassword", 1500);
//        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        UserTestData.USER_MATCHER.assertMatch(userRepository.getExisted(UserTestData.USER_ID), UsersUtil.updateFromTo(new User(UserTestData.user), updatedTo));
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void updateInvalid() throws Exception {
//        UserTo updatedTo = new UserTo(null, null, "password", null, 1);
//        perform(MockMvcRequestBuilders.put(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void updateDuplicate() throws Exception {
//        UserTo updatedTo = new UserTo(null, "newName", UserTestData.ADMIN_MAIL, "newPassword", 1500);
//        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
//    }
//
//    @Test
//    @WithUserDetails(value = UserTestData.USER_MAIL)
//    void getWithRestaurants() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + "/with-restaurants"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(UserTestData.USER_WITH_RESRAURANTS_MATCHER.contentJson(UserTestData.user));
//    }
//
//    @Test
//    void register() throws Exception {
//        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword", 1500);
//        User newUser = UsersUtil.createNewFromTo(newTo);
//        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newTo)))
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        User created = UserTestData.USER_MATCHER.readFromJson(action);
//        int newId = created.id();
//        newUser.setId(newId);
//        UserTestData.USER_MATCHER.assertMatch(created, newUser);
//        UserTestData.USER_MATCHER.assertMatch(userRepository.getExisted(newId), newUser);
//    }
//
//    @Test
//    void registerInvalid() throws Exception {
//        UserTo newTo = new UserTo(null, null, null, null, 1);
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//}