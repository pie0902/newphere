package news.newsphere.controller.comment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.comment.CommentRequest;
import news.newsphere.dto.comment.CommentResponse;
import news.newsphere.entity.user.User;
import news.newsphere.service.comment.CommentService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController{
    private final CommentService commentService;
    //댓글 생성
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
        @PathVariable Long postId,
        @RequestBody CommentRequest commentRequest,
        @AuthenticationPrincipal User user
    ) {
        CommentResponse commentResponse = commentService.createComment(postId,commentRequest,user);
        return ResponseEntity.ok().body(commentResponse);
    }
    //게시글 댓글 조회
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
        @PathVariable Long postId
    ) {
        List<CommentResponse> commentList = commentService.getComments(postId);
        return ResponseEntity.ok().body(commentList);
    }
    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
        @RequestBody CommentRequest commentRequest,
        @AuthenticationPrincipal User user,
        @PathVariable Long commentId
    ) {
        CommentResponse commentResponse = commentService.updateComment(commentRequest,user,commentId);
        return ResponseEntity.ok().body(commentResponse);
    }
    //댓글 삭제
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
        @PathVariable Long commentId,@AuthenticationPrincipal User user) {
        commentService.deleteComment(user,commentId);
        return ResponseEntity.ok().body("댓글이 성공적으로 삭제되었습니다");
    }

}
