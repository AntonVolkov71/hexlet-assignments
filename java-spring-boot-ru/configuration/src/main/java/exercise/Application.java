package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserProperties userProperties;

    // Все пользователи
    private List<User> users = Data.getUsers();

    // BEGIN
    @GetMapping("/admins")
    public List<String> getAdmins() {
        List<String> admins = userProperties.getAdmins();

        List<String> adminNames = users.stream()
                .filter(user -> admins.contains(user.getEmail()))
                .map(User::getName)
                .sorted()
                .toList();

        return adminNames;
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
