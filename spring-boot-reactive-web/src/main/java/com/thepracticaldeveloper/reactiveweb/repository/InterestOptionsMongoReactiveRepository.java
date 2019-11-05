package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface InterestOptionsMongoReactiveRepository extends ReactiveCrudRepository<InterestOptions, String> {
    //Mono<Interests> findInterestsByUser(String id);
}
