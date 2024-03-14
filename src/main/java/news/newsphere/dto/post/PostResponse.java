package news.newsphere.dto.post;

import lombok.Getter;
import news.newsphere.entity.post.Post;

@Getter
public class PostResponse {
    private String title;
    private String content;

    public PostResponse(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
