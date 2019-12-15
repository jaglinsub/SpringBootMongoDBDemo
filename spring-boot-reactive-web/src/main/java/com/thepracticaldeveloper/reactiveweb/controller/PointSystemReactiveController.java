package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.PointSystem;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.repository.ExperienceMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.PointSystemMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.ProfileMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/pointsystem")
public class PointSystemReactiveController {

    private static final Logger log = LoggerFactory.getLogger(PointSystemReactiveController.class);
    private PointSystemMongoReactiveRepository pointSystemMongoReactiveRepository;

    public PointSystemReactiveController(final PointSystemMongoReactiveRepository pointSystemMongoReactiveRepository) {
        this.pointSystemMongoReactiveRepository = pointSystemMongoReactiveRepository;
    }
    @GetMapping()
    public Flux<PointSystem> getPointSystem() {
        Flux<PointSystem> pointSystem = pointSystemMongoReactiveRepository.findAll();
        return pointSystem;
    }
}
