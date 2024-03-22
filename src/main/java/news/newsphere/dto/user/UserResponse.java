package news.newsphere.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import news.newsphere.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private String token;

    public UserResponse(User user,String token) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.token = token;
    }
    public UserResponse(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
