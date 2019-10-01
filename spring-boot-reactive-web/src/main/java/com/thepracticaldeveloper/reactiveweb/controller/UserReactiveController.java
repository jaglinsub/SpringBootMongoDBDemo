package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.User;
import com.thepracticaldeveloper.reactiveweb.domain.UserType;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserTypeMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserReactiveController {

    private static final Logger log = LoggerFactory.getLogger(UserReactiveController.class);
    private UserMongoReactiveRepository userMongoReactiveRepository;
    private UserTypeMongoReactiveRepository userTypeMongoReactiveRepository;

    public UserReactiveController(final UserMongoReactiveRepository userMongoReactiveRepository, final UserTypeMongoReactiveRepository userTypeMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
        this.userTypeMongoReactiveRepository = userTypeMongoReactiveRepository;
    }

    @PostMapping("/saveuser")
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody User userEntry) {
        if(userEntry.getUserType() != null && userEntry.getUserType().getTypeName() != null)
        {
            String userType = userEntry.getUserType().getTypeName();
            log.info("Entire User Object=" + userEntry);
            log.info("userType from request=" + userType);
            UserType userTypeMono = userTypeMongoReactiveRepository.findUserByTypeName(userType).block();
            log.info(userType + "=userTypeMon=" + userTypeMono);
            if(userTypeMono != null)
            {
                //userEntry.getUserType().setId(userTypeMono.getId());
                userEntry.setUserType(userTypeMono);
            }
            else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person Type Not Found");
            }
        }
        return userMongoReactiveRepository.save(userEntry).block();
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
