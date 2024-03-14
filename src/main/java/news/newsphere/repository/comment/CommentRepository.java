package news.newsphere.repository.comment;

import java.util.List;
import news.newsphere.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("SELECT c FROM Comment c WHERE c.post_id = :post_id")
    List<Comment> findByPostId(Long post_id);
}
