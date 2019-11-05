package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProfileMongoReactiveRepository extends ReactiveCrudRepository<Profile, String> {
    Mono<Profile> findProfileByUserId(String id);
}
