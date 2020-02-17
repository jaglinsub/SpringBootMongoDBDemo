package com.thepracticaldeveloper.reactiveweb.controller;

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
            emailService.sendWelcomeParentEmail(dbParentUser, savedUser, parentMongoReactiveRepository);
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
