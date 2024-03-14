package news.newsphere.dto.comment;

import lombok.Getter;
import news.newsphere.entity.comment.Comment;

@Getter
public class CommentResponse {
    private String comment;
    public CommentResponse(Comment comment){
        this.comment = comment.getComment();

    }
}
