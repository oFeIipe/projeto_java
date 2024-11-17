package projeto.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.entidades.Post;
import projeto.entidades.Usuario;
import projeto.repositories.PostRepository;


import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Post createPost(Post post){

        Usuario usuarioLogado = userService.getUsuarioLogado();

        if(usuarioLogado == null){
            return null;
        }
        post.setUsuario(usuarioLogado);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post editPost(String id, Post post){
        Usuario usuarioLogado = userService.getUsuarioLogado();
        Optional<Post> optionalPost = postRepository.findById(id);

        if (usuarioLogado == null || (optionalPost.isPresent() && !(optionalPost.get().getUsuario().getId().equals(usuarioLogado.getId())))) {
            return null;
        }
        if (optionalPost.isPresent()) {
            Post postEditado = optionalPost.get();
            postEditado.setConteudo(post.getConteudo());
            return postRepository.save(postEditado);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void deletePost(String id){
        Usuario usuarioLogado = userService.getUsuarioLogado();
        Optional<Post> optionalPost = postRepository.findById(id);

        if(usuarioLogado == null || (optionalPost.isPresent() && !(optionalPost.get().getUsuario().getId().equals(usuarioLogado.getId())))){
            return;
        }
        postRepository.deleteById(id);
    }
}
