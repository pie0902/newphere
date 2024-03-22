package news.newsphere.service.post;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.post.PostRequest;
import news.newsphere.dto.post.PostResponse;
import news.newsphere.entity.post.Post;
import news.newsphere.entity.user.User;
import news.newsphere.repository.post.PostRepository;
import news.newsphere.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //memo
    //security 사용으로 token 유효성 검사를 삭제할수 있었음
    public PostResponse createPost(PostRequest postRequest, User user) {
        // 사용자 정보 조회
        User foundUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new PostResponse(postRepository.save(new Post(postRequest, foundUser)));
    }
    public Page<PostResponse> getAllPost(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllWithUserUsePageble(pageable); // 변경: 페이징 처리를 위한 메서드 사용
        return postPage.map(post -> new PostResponse(post)); // 변경: Page 객체를 직접 변환
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        PostResponse postResponse = new PostResponse(post);
        return postResponse;
    }

    @Transactional
    public void updatePost(Long postId, PostRequest postRequest, Long userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("해당 포스트를 수정할 권한이 없습니다.");
        }
        post.updatePost(postRequest);
    }

    //게시글 삭제 메서드
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("해당 포스트를 수정할 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    public List<PostResponse> getUsersPost(List<Long> userIds
    ) {
        List<Post> postList = postRepository.findByUserIds(userIds);
        List<PostResponse> postResponseList = postList.stream()
            .map(post -> new PostResponse(post))
            .collect(Collectors.toList());
        return postResponseList;
    }
}

//
//    //게시글 전체조회
//    public List<PostResponse> getAllPost(Pageable pageable) {
//        List<Post> postList = postRepository.findAllWithUser();
//        List<PostResponse> postResponses = postList.stream()
//            .map(post -> new PostResponse(post))
//            .collect(Collectors.toList());
//        return postResponses;
//    }
// 게시글 전체조회 with 페이징
