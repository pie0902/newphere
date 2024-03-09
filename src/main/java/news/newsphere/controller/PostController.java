package news.newsphere.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.PostRequest;
import news.newsphere.dto.PostResponse;
import news.newsphere.entity.User;
import news.newsphere.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(
        @RequestBody PostRequest postRequest,
        @AuthenticationPrincipal User userDetails)
    {
            PostResponse postResponse = postService.createPost(postRequest, userDetails.getId()); // 인증 객체를 직접 전달
            return ResponseEntity.ok().body(postResponse);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPost()
    {
        List<PostResponse> postList = postService.getAllPost();
        return ResponseEntity.ok(postList);
    }
}
