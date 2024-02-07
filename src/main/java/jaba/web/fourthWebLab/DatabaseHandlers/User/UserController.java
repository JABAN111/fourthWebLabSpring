package jaba.web.fourthWebLab.DatabaseHandlers.User;

import jaba.web.fourthWebLab.DatabaseHandlers.Exceptions.UserNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository repository;
//    private final UserModelAssembler assembler;

    public UserController(UserRepository repository){//, UserModelAssembler assembler) {
        this.repository = repository;
//        this.assembler = assembler;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }
//    CollectionModel<EntityModel<User>> all() {
//
//        List<EntityModel<User>> employees = repository.findAll().stream() //
//                .map(assembler::toModel) //
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(employees, linkTo(methodOn(UserController.class).all()).withSelfRel());
//    }
    @PostMapping("/users")
    User newEmployee(@RequestBody User newUser) {
        return repository.save(newUser);
    }
//    @GetMapping("/users/{id}")
//    EntityModel<User> one(@PathVariable Long id) {
//        User user = repository.findById(id) //
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        return assembler.toModel(user);
//    }
@GetMapping("/users/{id}")
User one(@PathVariable Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    newUser.setLogin(newUser.getLogin());
                    //нужно добавить, чтобы после получения пароля была его расшифровка
                    newUser.setPassword(newUser.getPassword());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }
    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
