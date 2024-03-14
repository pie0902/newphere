package news.newsphere.entity.comment;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.newsphere.dto.comment.CommentRequest;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long post_id;
    private Long user_id;
    public Comment(Long post_id, CommentRequest commentRequest,Long user_id) {
        this.comment = commentRequest.getComment();
        this.createdAt = LocalDateTime.now();
        this.post_id = post_id;
        this.user_id = user_id;
    }
    public void updateComment(CommentRequest commentRequest) {
        this.comment = commentRequest.getComment();
        this.modifiedAt = LocalDateTime.now();
    }
}
