package news.newsphere.repository.post;


import java.util.List;
import news.newsphere.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findAllWithUser();
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds")
    List<Post> findByUserIds(@Param("userIds") List<Long> userIds);
}
