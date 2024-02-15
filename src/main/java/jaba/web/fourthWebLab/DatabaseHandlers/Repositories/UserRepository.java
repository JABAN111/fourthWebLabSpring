package jaba.web.fourthWebLab.DatabaseHandlers.Repositories;

import jaba.web.fourthWebLab.DatabaseHandlers.Entitities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
