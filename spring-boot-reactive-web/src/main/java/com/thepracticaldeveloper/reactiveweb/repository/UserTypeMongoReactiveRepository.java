package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.UserType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserTypeMongoReactiveRepository extends ReactiveCrudRepository<UserType, String> {
    Mono<UserType> findUserByTypeName(String typeName);
}
