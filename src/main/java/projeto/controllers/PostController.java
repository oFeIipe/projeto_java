package projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import projeto.entidades.Post;
import projeto.services.PostService;


import java.util.List;


@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        Post novoPost = postService.createPost(post);
        return new ResponseEntity<>(novoPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Post> editPost(@PathVariable String id, @RequestBody Post post) {

        Post postEditado = postService.editPost(id, post);
        return ResponseEntity.ok(postEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}