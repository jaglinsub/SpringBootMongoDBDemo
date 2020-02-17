package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;

public interface ParentMongoReactiveRepository extends ReactiveCrudRepository<ParentUser, String> {
    Mono<ParentUser> findParentUserById(String id);
    Mono<ParentUser> findByEmail(String email);
}
