package jaba.web.fourthWebLab.DatabaseHandlers.Repositories;

import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.Result;
import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result,Long> {

    List<Result> findByUser(User user);

    void deleteAllByUser(User user);
}
