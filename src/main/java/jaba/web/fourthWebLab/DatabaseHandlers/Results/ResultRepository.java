package jaba.web.fourthWebLab.DatabaseHandlers.Results;

import jaba.web.fourthWebLab.DatabaseHandlers.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result,Long> {

    List<Result> findAllByX(Double x);
    List<Result> findByUser(User user);

    void deleteAllByUser(User user);
}
