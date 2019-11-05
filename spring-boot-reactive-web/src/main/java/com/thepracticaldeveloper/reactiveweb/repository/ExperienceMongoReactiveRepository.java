package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Experience;
import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExperienceMongoReactiveRepository extends ReactiveCrudRepository<Experience, String> {
    //Mono<Interests> findInterestsByUser(String id);
}
