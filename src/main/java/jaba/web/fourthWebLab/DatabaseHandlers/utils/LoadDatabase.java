package jaba.web.fourthWebLab.DatabaseHandlers.utils;

import jaba.web.fourthWebLab.DatabaseHandlers.User.User;
import jaba.web.fourthWebLab.DatabaseHandlers.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            System.out.println("init data base");
            log.info("Preloading " + repository.save(new User("Baggin", "baggin")));
            log.info("Preloading " + repository.save(new User("bagin u", "tatitatu")));
        };
    }

}