package news.newsphere.repository.comment;

import java.util.Optional;
import news.newsphere.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
