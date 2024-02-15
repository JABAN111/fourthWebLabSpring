package jaba.web.fourthWebLab.DatabaseHandlers.Controllers;

import jaba.web.fourthWebLab.DatabaseHandlers.StatusCode.NewUserStatus;
import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.User;
import jaba.web.fourthWebLab.DatabaseHandlers.Repositories.UserRepository;
import jaba.web.fourthWebLab.DatabaseHandlers.StatusCode.UserInformationStatus;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;


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
    NewUserStatus newUser(@RequestBody User newUser) {
        if (repository.findById(newUser.getLogin()).isPresent()) {
            return NewUserStatus.USER_ALREADY_EXIST;
        } else {
            String hashedPassword = User.encryptStringMD2(newUser.getPassword() + salt);
            newUser.setPassword(hashedPassword);
            repository.save(newUser);
            return NewUserStatus.SUCCESSFULLY_CREATED;
        }
    }

    @CrossOrigin
    @PostMapping("/users/check")
    public UserInformationStatus checkUser(@RequestBody User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String hashedPassword = User.encryptStringMD2(password + salt);

        if (repository.findById(login).isPresent()) {
            if (repository.findById(login).get().getPassword().equals(hashedPassword)) {
                return UserInformationStatus.USER_VALID;
            } else {
                return UserInformationStatus.PASSWORD_INVALID;
            }
        }
        return UserInformationStatus.USER_NOT_FOUND;
    }

    @CrossOrigin
    @DeleteMapping("/user/{login}")
    void deleteUser(@PathVariable String login) {
        repository.deleteById(login);
    }
}
