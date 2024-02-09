package jaba.web.fourthWebLab.DatabaseHandlers.Results;

import jaba.web.fourthWebLab.DatabaseHandlers.Exceptions.ResultNotFoundException;
import jaba.web.fourthWebLab.DatabaseHandlers.Exceptions.UserNotFoundException;
import jaba.web.fourthWebLab.DatabaseHandlers.User.User;
import jaba.web.fourthWebLab.DatabaseHandlers.User.UserController;
import jaba.web.fourthWebLab.DatabaseHandlers.User.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin()
public class ResultController {
    private final ResultRepository repository;
    private final ResultModelAssembler assembler;

    public ResultController(ResultRepository repository, ResultModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    @GetMapping("/results")
    CollectionModel<EntityModel<Result>> all() {
        List<EntityModel<Result>> results = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());
        System.out.println("Данные отправляются...");
        return CollectionModel.of(results, linkTo(methodOn(ResultController.class).all()).withSelfRel());
    }
    @PostMapping("/results")
    Result newResult(@RequestBody Result newResult) {
        System.out.println("у нас новый результат" + newResult);
        return repository.save(newResult);
    }

    @GetMapping("/results/{id}")
    EntityModel<Result> one(@PathVariable Long id) {
        Result result = repository.findById(id) //
                .orElseThrow(() -> new ResultNotFoundException(id));

        return assembler.toModel(result);
    }
    @PutMapping("/results/{id}")
    Result replaceResult(@RequestBody Result newResult, @PathVariable Long id) {
        return repository.findById(id)
                .map(result -> {
                    newResult.setX(newResult.getX());
                    newResult.setY(newResult.getY());
                    newResult.setR(newResult.getR());
                    newResult.setDate(newResult.getDate());
                    newResult.setHit(newResult.getHit());
                    return repository.save(result);
                })
                .orElseGet(() -> {
                    newResult.setId(id);
                    return repository.save(newResult);
                });
    }
    @DeleteMapping("/results/{id}")
    void deleteResult(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
