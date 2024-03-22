package news.newsphere.controller.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.PageResponse;
import news.newsphere.dto.post.PostRequest;
import news.newsphere.dto.post.PostResponse;
import news.newsphere.entity.post.Post;
import news.newsphere.entity.user.User;
import news.newsphere.service.RssService;
import news.newsphere.service.post.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        @AuthenticationPrincipal User user
    ) {
        PostResponse postResponse = postService.createPost(postRequest,user); // 인증 객체를 직접 전달
        return ResponseEntity.ok().body(postResponse);
    }
    //전체조회
//    @GetMapping("/posts")
//    public ResponseEntity<List<PostResponse>> getAllPost()
//    {
//        List<PostResponse> postList = postService.getAllPost();
//        return ResponseEntity.ok(postList);
//    }

    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PostResponse>> getPosts(
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PostResponse> postPage = postService.getAllPost(pageable); // 가정: 서비스 메서드 수정 필요
        int totalItems = (int) postPage.getTotalElements();
        int totalPages = postPage.getTotalPages();
        int currentPage = postPage.getNumber();

        PageResponse<PostResponse> response = new PageResponse<>(postPage.getContent(), currentPage, totalItems, totalPages);
        return ResponseEntity.ok(response);
    }
    //선택조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId)
    {
        PostResponse postResponse = postService.getPost(postId);
        return ResponseEntity.ok(postResponse);
    }
    @GetMapping("/posts/getUsersPost")
    //특정 유저 아이디 글들 조회
    public ResponseEntity<List<PostResponse>> getUsersPost(@RequestBody List<Long> userIds){
        List<PostResponse> postList = postService.getUsersPost(userIds);
        return ResponseEntity.ok(postList);
    }


    //게시글 수정
    @PutMapping("/posts/{postId}")
    public void updatePost(
        @PathVariable Long postId,
        @RequestBody PostRequest postRequest,
        @AuthenticationPrincipal User userDetails
    ) {
        if (userDetails == null) {
            // 사용자 정보가 없는 경우 에러 응답
            throw new IllegalArgumentException("유저 정보 없음");
        }
        postService.updatePost(postId, postRequest, userDetails.getId());
    }
    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(
        @PathVariable Long postId,
        @AuthenticationPrincipal User userDetails
    ) {
        postService.deletePost(postId,userDetails.getId());
        return ResponseEntity.ok().body("삭제에 성공 했습니다.");
    }

}
