package news.newsphere.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import news.newsphere.dto.PostRequest;
import news.newsphere.dto.PostResponse;
import news.newsphere.entity.Post;
import news.newsphere.entity.User;
import news.newsphere.repository.PostRepository;
import news.newsphere.repository.UserRepository;
import news.newsphere.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    //memo
    //토큰 유효성 검사와 사용자 정보 조회하는 레이어를 나눠놓는게 좋을것 같음
    public PostResponse createPost(PostRequest postRequest, String token) {
        //토큰 유효성 검사
        if (!jwtUtils.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰 입니다.");
        }
        Long userId = jwtUtils.getUserIdFromToken(token);
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post = new Post(postRequest, user);
        postRepository.save(post);
        PostResponse postResponse = new PostResponse(post);
        return postResponse;
    }
    //게시글 전체조회
    public List<PostResponse> getAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> postResponses = postList.stream()
            .map(post -> new PostResponse(post))
            .collect(Collectors.toList());
        return postResponses;
    }
}
