package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileReactiveController {

    private static final Logger log = LoggerFactory.getLogger(ProfileReactiveController.class);
    private ProfileMongoReactiveRepository profileMongoReactiveRepository;
    private ExperienceMongoReactiveRepository  experienceMongoReactiveRepository;

    public ProfileReactiveController(final ProfileMongoReactiveRepository profileMongoReactiveRepository, final ExperienceMongoReactiveRepository  experienceMongoReactiveRepository) {
        this.profileMongoReactiveRepository = profileMongoReactiveRepository;
        this.experienceMongoReactiveRepository = experienceMongoReactiveRepository;
    }
    @GetMapping()
    public Profile getInterestsByUserId(final @RequestParam(name = "userid") String id) {
        Profile profile = profileMongoReactiveRepository.findProfileByUserId(id).block();
        return profile;
    }

    @PostMapping("/saveprofile")
    @ResponseStatus(HttpStatus.CREATED)
    Profile create(@RequestBody Profile userEntry) {
        if(userEntry != null) {
            log.info("Entire Profile Object=" + userEntry.toString());
        }
        return this.profileMongoReactiveRepository.save(userEntry).block();
    }
}
