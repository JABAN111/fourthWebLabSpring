package jaba.web.fourthWebLab.DatabaseHandlers.User;

import jaba.web.fourthWebLab.DatabaseHandlers.Exceptions.UserNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin()

public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }
    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        System.out.println("у нас новый результат" + newUser);
        return repository.save(newUser);
    }
    @PostMapping("/users/{login}")
    EntityModel<User> one(@PathVariable String login) {
        User user = repository.findById(login)
                .orElseThrow(() -> new UserNotFoundException(login));

        return assembler.toModel(user);
    }

//    @PutMapping("/users/{id}")
//    User replaceUser(@RequestBody User newUser, @PathVariable String login) {
//
//        return repository.findById(id)
//                .map(employee -> {
//                    newUser.setLogin(newUser.getLogin());
//                    //нужно добавить, чтобы после получения пароля была его расшифровка
//                    newUser.setPassword(newUser.getPassword());
//                    return repository.save(employee);
//                })
//                .orElseGet(() -> {
//                    newUser.setId(id);
//                    return repository.save(newUser);
//                });
//    }
    @DeleteMapping("/user/{login}")
    void deleteUser(@PathVariable String login) {
        repository.deleteById(login);
    }
}
