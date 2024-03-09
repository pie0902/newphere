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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    //게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(
        @RequestBody PostRequest postRequest,
        @RequestHeader(value = "Authorization") String token)
    {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        PostResponse postResponse = postService.createPost(postRequest,cleanedToken);
        return ResponseEntity.ok().body(postResponse);
    }
    //전체조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPost()
    {
        List<PostResponse> postList = postService.getAllPost();
        return ResponseEntity.ok(postList);
    }

}
