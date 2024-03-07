package news.newsphere.utils;

import lombok.RequiredArgsConstructor;
import news.newsphere.entity.User;
import news.newsphere.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userData = userRepository.findByEmail(email)
            .orElseThrow(()->new UsernameNotFoundException("유저를 찾을수가 없습니다."));
        return new CustomUserDetails(userData);
    }

}
