package es.ethcero.mca.blogcero.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import es.ethcero.mca.blogcero.Views.Views;
import es.ethcero.mca.blogcero.models.Author;
import es.ethcero.mca.blogcero.restModels.NewComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import es.ethcero.mca.blogcero.models.Comment;
import es.ethcero.mca.blogcero.models.Post;
import es.ethcero.mca.blogcero.responses.ResponseCreatedOrNotFound;
import es.ethcero.mca.blogcero.responses.ResponseObjectOrNotFound;
import es.ethcero.mca.blogcero.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostRESTController {

    @Autowired
    private PostService service;

    @GetMapping("")
    @JsonView(Views.Basic.class)
    List<Post> getPosts() {

        return this.service.getPosts();
    }

    @GetMapping("/{postId}")
    @JsonView(Views.Full.class)
    ResponseEntity<Post> getPost(@PathVariable long postId) {

        Optional<Post> post = this.service.getPost(postId);

        return (ResponseEntity<Post>) new ResponseObjectOrNotFound(post).response();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Post postPost(@RequestBody Post post){

        return this.service.addPost(post);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Comment> postComment(@PathVariable long postId, @RequestBody NewComment comment) {

        Optional<Comment> comment1 = this.service.addComment(postId, comment);
        return (ResponseEntity<Comment>) new ResponseCreatedOrNotFound(comment1).response();
    }

    @GetMapping("/{postId}/comments")
    @JsonView(Views.Full.class)
    List<Comment> postComment(@PathVariable long postId) {
        return this.service.getComments(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    ResponseEntity<Comment> postComment(@PathVariable long postId, @PathVariable long commentId) {

        Optional<Comment> comment = this.service.getComment(commentId);
        return (ResponseEntity<Comment>) new ResponseObjectOrNotFound(comment).response();
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    ResponseEntity deleteComment(@PathVariable long postId, @PathVariable long commentId) {

        this.service.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

}
