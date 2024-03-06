package news.newsphere.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.newsphere.dto.UserSignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name="users")
@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    public User(UserSignupRequest userSignupRequest,String pw) {
        this.username = userSignupRequest.getUsername();
        this.email = userSignupRequest.getEmail();
        this.password = pw;
    }
}
