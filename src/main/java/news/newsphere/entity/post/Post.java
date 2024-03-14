package news.newsphere.entity.post;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import news.newsphere.dto.post.PostRequest;
import news.newsphere.entity.user.User;

@Getter
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(PostRequest postRequest,User user){
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }
    public void updatePost(PostRequest postRequest){
        this.title = postRequest.getTitle();
        this.content= postRequest.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
