package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserTypeMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserDataLoader.class);

    private UserMongoReactiveRepository userMongoReactiveRepository;
    private UserTypeMongoReactiveRepository userTypeMongoReactiveRepository;

    UserDataLoader(final UserMongoReactiveRepository userMongoReactiveRepository, final UserTypeMongoReactiveRepository userTypeMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
        this.userTypeMongoReactiveRepository = userTypeMongoReactiveRepository;
    }

    @Override
    public void run(final String... args) throws Exception {

    }

    public void run2(final String... args) throws Exception {
        /*try {
            Flux<User> userMono = userMongoReactiveRepository.findByEmail(Mono.just("test@test.com"));
            log.info("User Existis" + userMono.count().block());

            //if (userMono.count().block() != 0L) {
                log.info("User does not existis, creating user");
                //UserType userType = new UserType("Student", "Student");
                UserType userTypeTmp = new UserType("Student", "Student");
                UserType userType = userTypeMongoReactiveRepository.save(userTypeTmp).block();

            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date today = new Date();
                Date todayWithZeroTime = formatter.parse(formatter.format(today));
                //String firstName, String lastName, String email, Date dateofBirth, String grade, String location, String phoneNumber, String organizationName, UserType userType
                User user = new User(
                        "TestFN", "TestLN", "test@test.com", todayWithZeroTime, "9", "Lakeville", "612-222-2222", "SHPA", userType
                );
                userMongoReactiveRepository.save(user).block();
            userMono = userMongoReactiveRepository.findByEmail(Mono.just("test@test.com"));
            log.info("User Existis" + userMono.count().block());
                log.info("Successfully created user!!!");
            //}
        }
        catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }*/
    }

}
