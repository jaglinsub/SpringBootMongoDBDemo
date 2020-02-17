package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.EmailStatus;
import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import com.thepracticaldeveloper.reactiveweb.domain.UserType;
import com.thepracticaldeveloper.reactiveweb.repository.ParentMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserTypeMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/user")
public class UserReactiveController {

    private static final Logger log = LoggerFactory.getLogger(UserReactiveController.class);
    private UserMongoReactiveRepository userMongoReactiveRepository;
    private UserTypeMongoReactiveRepository userTypeMongoReactiveRepository;
    private ParentMongoReactiveRepository parentMongoReactiveRepository;

    @Autowired
    private EmailService emailService;

    public UserReactiveController(final UserMongoReactiveRepository userMongoReactiveRepository, final UserTypeMongoReactiveRepository userTypeMongoReactiveRepository,
                                  final ParentMongoReactiveRepository parentMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
        this.userTypeMongoReactiveRepository = userTypeMongoReactiveRepository;
        this.parentMongoReactiveRepository = parentMongoReactiveRepository;
    }

    @PostMapping("/saveuser")
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody User userEntry) {

        if (userEntry.getUserType() != null && userEntry.getUserType().getTypeName() != null) {
            String userType = userEntry.getUserType().getTypeName();
            log.info("Entire User Object=" + userEntry);
            log.info("userType from request=" + userType);
            UserType userTypeMono = userTypeMongoReactiveRepository.findUserByTypeName(userType).block();
            log.info(userType + "=userTypeMon=" + userTypeMono);
            if (userTypeMono != null) {
                //userEntry.getUserType().setId(userTypeMono.getId());
                userEntry.setUserType(userTypeMono);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person Type Not Found");
            }
        }
        ParentUser dbParentUser = null;
        if (userEntry.getParentUser() != null) {
            dbParentUser = createParent(userEntry.getParentUser());
            log.info("Parent User=" + dbParentUser.toString());
            userEntry.setParentUser(dbParentUser);
        }

        User savedUser = userMongoReactiveRepository.save(userEntry).block();

        if (dbParentUser != null && !dbParentUser.isEmailSent()) {
            log.info("Starting Email Sending process.......");
            sendWelcomeParentEmail(dbParentUser, savedUser);
        }
        log.info("Returning after saving user....");
        return savedUser;
    }

    private ParentUser createParent(ParentUser parentUser) {
        String inputEmail = parentUser.getEmail();
        ParentUser dbPU = null;
        if(inputEmail != null) {
            dbPU = parentMongoReactiveRepository.findByEmail(inputEmail).block();
        }

        if (dbPU != null) {
            if (dbPU.getEmail() != null && dbPU.getEmail().equalsIgnoreCase(inputEmail)) {
                log.info("Parent User=" + dbPU.toString());
                return dbPU;
            } else {
                return parentMongoReactiveRepository.save(parentUser).block();
            }
        } else {
            return parentMongoReactiveRepository.save(parentUser).block();
        }

    }

    private void sendWelcomeParentEmail(ParentUser parentUser, User user) {
        if (parentUser != null && !parentUser.isEmailSent()) {
            String subject = "Welcome to 'Next Step'";
            String body = String.join(
                    System.getProperty("line.separator"),
                    "<h1>Welcome to 'Next Step</h1>",
                    "<p>Click on the below link to buy the subscription for your child." + user.getFirstName() + " " + user.getLastName(),
                    //"<a href='http://ec2-18-218-102-11.us-east-2.compute.amazonaws.com/login/1/" +user.getId() + "'>Subscribe</a>."
                    "<a href='http://localhost:4200/login/1/" + user.getId() + "'>Subscribe</a>."
            );
            try {
                log.info("Started Sending Email");
                Future<EmailStatus> futureEmailStatus = emailService.email(parentUser.getEmail(), subject, body);
                while (true) {
                    if (futureEmailStatus.isDone()) {
                        EmailStatus emailStatus = futureEmailStatus.get();
                        log.info("Result from asynchronous process - " + emailStatus);
                        if (emailStatus != null && emailStatus.isEmailSent()) {
                            parentUser.setEmailSent(true);
                        }
                        else {
                            parentUser.setEmailSent(false);
                            log.error("Email Error::" + emailStatus.getErrorMessage());
                        }
                        parentMongoReactiveRepository.save(parentUser).block();
                        log.info("Email Sent=" + user.getParentUser().isEmailSent());
                        break;
                    }
                    log.info("Continue doing something else. ");
                    Thread.sleep(500);
                }
                log.info("Done Sending Email");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping()
    public User getUserById(final @RequestParam(name = "id") String id) {
        return userMongoReactiveRepository.findUserById(id).block();
    }

    //@GetMapping("/{email}")
    @GetMapping("/email")
    //@RequestMapping(value="{email}",method={RequestMethod.GET,RequestMethod.DELETE})
    public User userByEmail(final @RequestParam(name = "email") String email) {
        log.info("In userByEmail");
        return userMongoReactiveRepository.findByEmail(email).block();
    }
}
