package news.newsphere.controller.user;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import news.newsphere.dto.user.UserResponse;
import news.newsphere.dto.user.UserSignupRequest;
import news.newsphere.entity.user.User;
import news.newsphere.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureMockMvc(addFilters=false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 테스트")
    void signup_test() throws Exception {
        //given
        UserSignupRequest userSignupRequest = new UserSignupRequest("tester", "test@test.com", "1234");
        User user = new User(userSignupRequest,"encodedPassword");
        UserResponse userResponse = new UserResponse(user.getUsername(), user.getEmail(), null);
        given(userService.signUp(userSignupRequest)).willReturn(userResponse);

        //when
        ResultActions actions = mockMvc.perform(post("/api/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userSignupRequest)));

        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(userResponse.getUsername()))
            .andExpect(jsonPath("$.email").value(userResponse.getEmail()))
            .andDo(print());
    }
}
