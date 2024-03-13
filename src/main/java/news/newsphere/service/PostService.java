package news.newsphere.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.PostRequest;
import news.newsphere.dto.PostResponse;
import news.newsphere.entity.Post;
import news.newsphere.entity.User;
import news.newsphere.repository.PostRepository;
import news.newsphere.repository.UserRepository;
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

    //게시글 전체조회
    public List<PostResponse> getAllPost() {
        List<Post> postList = postRepository.findAllWithUser();
        List<PostResponse> postResponses = postList.stream()
            .map(post -> new PostResponse(post))
            .collect(Collectors.toList());
        return postResponses;
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
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("해당 포스트를 수정할 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    public List<PostResponse> getUsersPost(List<Long> userIds
    ) {
        List<Post> postList = postRepository.findByUserIds(userIds);
        List<PostResponse> postResponseList = postList.stream()
            .map(post->new PostResponse(post))
            .collect(Collectors.toList());
        return postResponseList;
    }
}
