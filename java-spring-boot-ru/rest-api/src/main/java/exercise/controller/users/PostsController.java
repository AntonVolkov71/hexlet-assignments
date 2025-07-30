package exercise.controller.users;

import java.util.List;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {
    @Setter()
    private static List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getPostById(@PathVariable Integer id) {
        List<Post> findPosts = posts
                .stream()
                .filter(p -> p.getUserId() == id)
                .toList();

        return ResponseEntity.ok()
                .body(findPosts);
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createById(@PathVariable Integer id, @RequestBody Post body) {
        Post post = new Post();
        post.setUserId(id);
        post.setSlug(body.getSlug());
        post.setTitle(body.getTitle());
        post.setBody(body.getBody());

        posts.add(post);

        return post;
    }
}
// END
