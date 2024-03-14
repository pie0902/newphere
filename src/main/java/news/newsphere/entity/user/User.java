package news.newsphere.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.newsphere.dto.user.UserSignupRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name="users")
@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum; // 사용자 권한

    public User(UserSignupRequest userSignupRequest, String pw) {
        this.username = userSignupRequest.getUsername();
        this.email = userSignupRequest.getEmail();
        this.password = pw;
        this.userRoleEnum = UserRoleEnum.USER;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 단일 UserRoleEnum 인스턴스를 사용하여 GrantedAuthority 컬렉션을 생성
        return Collections.singletonList(new SimpleGrantedAuthority(userRoleEnum.getKey()));
    }

    @Override
    public boolean isAccountNonExpired() {
        // 여기에 계정 만료 여부 로직 구현
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 여기에 계정 잠금 여부 로직 구현
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 여기에 비밀번호 만료 여부 로직 구현
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 여기에 계정 활성 상태 로직 구현
        return true;
    }
}

//


