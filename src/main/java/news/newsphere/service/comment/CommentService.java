package news.newsphere.service.comment;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.comment.CommentRequest;
import news.newsphere.dto.comment.CommentResponse;
import news.newsphere.entity.comment.Comment;
import news.newsphere.entity.user.User;
import news.newsphere.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    //댓글 생성
    public CommentResponse createComment(Long postId, CommentRequest commentRequest, User user
    ) {
        Comment comment = new Comment(postId,commentRequest, user.getId());
        commentRepository.save(comment);
        CommentResponse commentResponse = new CommentResponse(comment);
        return commentResponse;
    }
    //댓글 조회
    public List<CommentResponse> getComments(Long postId
    ) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponseList = commentList.stream()
            .map(comment ->new CommentResponse(comment))
            .collect(Collectors.toList());
        return commentResponseList;
    }
    //댓글 수정
    @Transactional
    public CommentResponse updateComment(CommentRequest commentRequest, User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을수 없습니다."));
        if (!comment.getUser_id().equals(user.getId())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다");
        }
        comment.updateComment(commentRequest);
        CommentResponse commentResponse = new CommentResponse(comment);
        return commentResponse;
    }
    //댓글 삭제
    public void deleteComment(User user,Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("댓글을 찾을수 없습니다."));
        if (!comment.getUser_id().equals(user.getId())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다");
        }
        else {
            commentRepository.deleteById(commentId);
        }
    }
}
