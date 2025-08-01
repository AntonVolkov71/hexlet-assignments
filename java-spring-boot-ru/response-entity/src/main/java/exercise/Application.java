package exercise;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;
import lombok.Setter;

import java.util.List;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    @Setter
    private static List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index() {
        var result = posts.stream().toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> show(@PathVariable String id) {
        var result = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        return ResponseEntity.of(result);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        posts.add(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(
            @PathVariable String id,
            @RequestBody Post body
    ) {
        var maybePage = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (maybePage.isPresent()) {
            var post = maybePage.get();
            post.setId(body.getId());
            post.setTitle(body.getTitle());
            post.setBody(body.getBody());
        }

        return ResponseEntity.of(maybePage);
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
