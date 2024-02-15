package jaba.web.fourthWebLab.DatabaseHandlers.Controllers;

import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.Result;
import jaba.web.fourthWebLab.DatabaseHandlers.Repositories.ResultRepository;
import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.User;
import jaba.web.fourthWebLab.DatabaseHandlers.Repositories.UserRepository;
import jaba.web.fourthWebLab.ResultProcessing.CoordinateProcessing.AreaProcessing;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
public class ResultController {
    private final ResultRepository resultRepository;
    private final AreaProcessing areaProcessing;
    private final UserRepository userRepository;
    public ResultController(ResultRepository repository, AreaProcessing areaProcessing, UserRepository userRepository) {
        this.resultRepository = repository;
        this.areaProcessing = areaProcessing;
        this.userRepository = userRepository;
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
    private boolean validateUser(User user){
        if(userRepository.findById(user.getLogin()).isPresent()){
            user.setPassword(User.encryptStringMD2(user.getPassword()+ UserController.getSalt()));
            User foundUser = userRepository.findById(user.getLogin()).get();
            return foundUser.getLogin().equals(user.getLogin()) && foundUser.getPassword().equals(user.getPassword());
        }
    return false;
    }
}
