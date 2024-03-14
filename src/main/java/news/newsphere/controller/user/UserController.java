package news.newsphere.controller.user;


import lombok.RequiredArgsConstructor;
import news.newsphere.dto.user.UserResponse;
import news.newsphere.dto.user.UserSigninRequest;
import news.newsphere.dto.user.UserSignupRequest;
import news.newsphere.service.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController{
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserSignupRequest userSignupRequest) {
        UserResponse userResponse = userService.signUp(userSignupRequest);
        return ResponseEntity.ok().body(userResponse);
    }
    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody UserSigninRequest userSigninRequest) {
        UserResponse userResponse = userService.signin(userSigninRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + userResponse.getToken());
        return new ResponseEntity<>(userResponse,headers,HttpStatus.OK);
    }

}
