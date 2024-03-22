package news.newsphere.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import news.newsphere.dto.user.UserResponse;
import news.newsphere.dto.user.UserSigninRequest;
import news.newsphere.dto.user.UserSignupRequest;
import news.newsphere.entity.user.User;
import news.newsphere.repository.user.UserRepository;
import news.newsphere.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private UserSignupRequest userSignupRequest;
    private UserSigninRequest userSigninRequest;

    @BeforeEach
    void setup() {
        userSignupRequest = new UserSignupRequest("tester", "test@test.com", "1234");
        userSigninRequest = new UserSigninRequest("test@test.com", "1234");
    }
    @Test
    @DisplayName("회원가입 테스트")
    void signup_test(){
        // given
        given(userRepository.findByEmail(userSignupRequest.getEmail())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(new User(userSignupRequest,"encodedPassword"));

        //when
        UserResponse userResponse = userService.signUp(userSignupRequest);

        //then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo(userSignupRequest.getEmail());
    }
    @Test
    @DisplayName("로그인 테스트")
    void signin_test(){
        //given
        User user = new User(userSignupRequest,"encodedPassword");
        given(userRepository.findByEmail(userSigninRequest.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(userSigninRequest.getPassword(),user.getPassword())).willReturn(true);
        given(jwtUtil.createToken(user.getEmail(),user.getUserRoleEnum())).willReturn("token");

        //when
        UserResponse userResponse = userService.signin(userSigninRequest);
        // then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo(userSigninRequest.getEmail());
        assertThat(userResponse.getToken()).isEqualTo("token");
    }


}
