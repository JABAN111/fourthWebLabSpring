package jaba.web.fourthWebLab.DatabaseHandlers.Results;

import jaba.web.fourthWebLab.DatabaseHandlers.Exceptions.ResultNotFoundException;
import jaba.web.fourthWebLab.DatabaseHandlers.User.User;
import jaba.web.fourthWebLab.DatabaseHandlers.User.UserController;
import jaba.web.fourthWebLab.DatabaseHandlers.User.UserRepository;
import jaba.web.fourthWebLab.ResultProcessing.CoordinateProcessing.AreaProcessing;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin()
public class ResultController {
    private final ResultRepository resultRepository;
    private final ResultModelAssembler assembler;
    private final AreaProcessing areaProcessing;
    private final UserRepository userRepository;
    public ResultController(ResultRepository repository, ResultModelAssembler assembler, AreaProcessing areaProcessing, UserRepository userRepository) {
        this.resultRepository = repository;
        this.assembler = assembler;
        this.areaProcessing = areaProcessing;
        this.userRepository = userRepository;
    }
    @GetMapping("/results/{id}")
    CollectionModel<EntityModel<Result>> all() {
        List<EntityModel<Result>> results = resultRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(results, linkTo(methodOn(ResultController.class).all()).withSelfRel());
    }
    @CrossOrigin
    @PostMapping("/result/getAllByUser")
    List<Result> sendUserResults(@RequestBody User user){
        return resultRepository.findByUser(user);
    }

    @CrossOrigin
    @PostMapping("/results")
    Result newResult(@RequestBody Result newResult) {
        Result result = new Result(newResult.getX(),newResult.getY(), newResult.getR(),
                areaProcessing.areaCheck(newResult.getX(), newResult.getY(), newResult.getR()),
                newResult.getDate(),newResult.getUser());

        return resultRepository.save(result);
    }

    @PostMapping("/results/{id}")
    EntityModel<Result> one(@PathVariable Long id) {
        Result result = resultRepository.findById(id) //
                .orElseThrow(() -> new ResultNotFoundException(id));

        return assembler.toModel(result);
    }
    @PutMapping("/results/{id}")
    Result replaceResult(@RequestBody Result newResult, @PathVariable Long id) {
        return resultRepository.findById(id)
                .map(result -> {
                    newResult.setX(newResult.getX());
                    newResult.setY(newResult.getY());
                    newResult.setR(newResult.getR());
                    newResult.setDate(newResult.getDate());
                    newResult.setHit(newResult.getHit());
                    return resultRepository.save(result);
                })
                .orElseGet(() -> {
                    newResult.setId(id);
                    return resultRepository.save(newResult);
                });
    }
    @PostMapping("/results/deleteRes")
    @CrossOrigin
    @Transactional
    void deleteResult(@RequestBody User user) {

        if(validateUser(user)) {
            resultRepository.deleteAllByUser(user);
        }
        resultRepository.findByUser(user).clear();
    }
    boolean validateUser(User user){
        if(userRepository.findById(user.getLogin()).isPresent()){
            user.setPassword(User.encryptStringMD2(user.getPassword()+ UserController.getSalt()));
            User foundUser = userRepository.findById(user.getLogin()).get();
            if(foundUser.getLogin().equals(user.getLogin()) &&
                foundUser.getPassword().equals(user.getPassword())
            ){
                return true;
            }

        }
    return false;
    }
}
