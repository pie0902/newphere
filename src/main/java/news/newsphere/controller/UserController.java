package news.newsphere.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.UserSigninRequest;
import news.newsphere.dto.UserSignupRequest;
import news.newsphere.dto.UserResponse;
import news.newsphere.service.UserService;
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
    public ResponseEntity<UserResponse> signin(@RequestBody UserSigninRequest userSigninRequest,
        HttpServletResponse res){
        UserResponse userResponse = userService.signin(userSigninRequest);
        res.addHeader("Authorization","Bearer "+userResponse.getToken());
        return ResponseEntity.ok().body(userResponse);
    }
}
