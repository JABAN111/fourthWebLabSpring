package jaba.web.fourthWebLab.DatabaseHandlers.User;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin
public class UserController {
    private final UserRepository repository;
    @Getter
    private static final String salt = "2Hq@*!8fdAQl";


    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    NewUser newUser(@RequestBody User newUser) {
        if (repository.findById(newUser.getLogin()).isPresent()) {
            return NewUser.USER_ALREADY_EXIST;
        } else {
            String hashedPassword = User.encryptStringMD2(newUser.getPassword() + salt);
            newUser.setPassword(hashedPassword);
            repository.save(newUser);
            return NewUser.SUCCESSFULLY_CREATED;
        }
    }

    @CrossOrigin
    @PostMapping("/users/check")
    public statusUserInformation checkUser(@RequestBody User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String hashedPassword = User.encryptStringMD2(password + salt);

        if (repository.findById(login).isPresent()) {
            if (repository.findById(login).get().getPassword().equals(hashedPassword)) {
                return statusUserInformation.USER_VALID;
            } else {
                return statusUserInformation.PASSWORD_INVALID;
            }
        }
        return statusUserInformation.USER_NOT_FOUND;
    }

    @CrossOrigin
    @DeleteMapping("/user/{login}")
    void deleteUser(@PathVariable String login) {
        repository.deleteById(login);
    }
}
