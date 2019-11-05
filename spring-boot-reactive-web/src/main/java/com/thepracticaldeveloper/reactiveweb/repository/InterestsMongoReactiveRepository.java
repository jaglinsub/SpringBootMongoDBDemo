package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface InterestsMongoReactiveRepository extends ReactiveCrudRepository<Interests, String> {
    Mono<Interests> findInterestsByUserId(String id);
}
