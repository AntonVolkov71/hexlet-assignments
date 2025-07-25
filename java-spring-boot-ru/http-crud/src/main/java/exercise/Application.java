package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import exercise.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> index(
            @RequestParam(name = "limit", defaultValue = "2") Integer limit,
            @RequestParam(name = "page", defaultValue = "10") Integer page
    ) {
        int offset = (page - 1) * limit;
        return posts.stream()
                .skip(offset)
                .limit(limit)
                .toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);

        return post;
    }

    @PutMapping("/posts/{id}")
    public Post update(
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

        return body;
    }

    @DeleteMapping("/posts/{id}")
    public void remove(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
