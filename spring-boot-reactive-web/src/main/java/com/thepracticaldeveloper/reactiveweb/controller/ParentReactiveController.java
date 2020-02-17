package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import com.thepracticaldeveloper.reactiveweb.repository.ParentMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/parent")
public class ParentReactiveController {

    private static final Logger log = LoggerFactory.getLogger(ParentReactiveController.class);
    private UserMongoReactiveRepository userMongoReactiveRepository;
    private ParentMongoReactiveRepository parentMongoReactiveRepository;

    public ParentReactiveController(final UserMongoReactiveRepository userMongoReactiveRepository, final ParentMongoReactiveRepository parentMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
        this.parentMongoReactiveRepository = parentMongoReactiveRepository;
    }

    @PostMapping("/createParent")
    @ResponseStatus(HttpStatus.CREATED)
    ParentUser create(@RequestBody ParentUser parentUser) {
        return parentMongoReactiveRepository.save(parentUser).block();
    }

    private boolean doParentExist(ParentUser parentUser) {
        if (parentUser != null && parentUser.getId() != null) {
            ParentUser dbParentUser = getParentUserById(parentUser.getId());
            if(dbParentUser != null) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/id")
    public ParentUser getParentUserById(final @RequestParam(name = "id") String id) {
        return parentMongoReactiveRepository.findParentUserById(id).block();
    }

    //@GetMapping("/{email}")
    @GetMapping("/email")
    //@RequestMapping(value="{email}",method={RequestMethod.GET,RequestMethod.DELETE})
    public ParentUser parentByEmail(final @RequestParam(name = "email") String email) {
        log.info("In userByEmail");
        return parentMongoReactiveRepository.findByEmail(email).block();
    }

    @GetMapping("/students")
    public Flux<User> getStudentByParentUserId(final @RequestParam(name = "id") String parentId) {
        log.info("Parent id for Students=" + parentId);
        User exampleUser = new User();
        exampleUser.setParentUser(new ParentUser(parentId));
//        List<User> users = this.userMongoReactiveRepository.findAll(Example.of(exampleUser)).collectList().block();
//        log.info("Students count=" + users.size());
//        for (User user: users) {
//            log.info("Student found for parent=" + user.getEmail());
//        }

//        List<User> users = this.userMongoReactiveRepository.findByParentUserId(new ObjectId(parentId)).collectList().block();
//        log.info("Students count=" + users.size());
//        for (User user: users) {
//            log.info("Student found for parent=" + user.toString());
//        }

//        return this.userMongoReactiveRepository.findAll(Example.of(exampleUser));

        return this.userMongoReactiveRepository.findByParentUserId(new ObjectId(parentId));
    }
}
