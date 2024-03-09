package news.newsphere.repository;


import java.util.Optional;
import news.newsphere.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

public interface PostRepository extends JpaRepository<Post,Long> {


}
