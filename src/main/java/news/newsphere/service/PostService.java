package news.newsphere.service;

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
    public PostResponse createPost(PostRequest postRequest,Long user_id) {
        // 사용자 정보 조회
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new PostResponse(postRepository.save(new Post(postRequest, user)));
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
