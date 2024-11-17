package projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.entidades.Post;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, String> {
    List<Post> findByUsuarioId(String userId);
}
