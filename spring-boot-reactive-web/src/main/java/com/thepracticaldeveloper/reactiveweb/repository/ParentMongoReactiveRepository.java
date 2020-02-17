package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ParentMongoReactiveRepository extends ReactiveCrudRepository<ParentUser, String> {
    Mono<ParentUser> findParentUserById(String id);
    Mono<ParentUser> findByEmail(String email);
}
