package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserMongoReactiveRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByEmail(String email);
    Mono<User> findUserById(String id);
}
