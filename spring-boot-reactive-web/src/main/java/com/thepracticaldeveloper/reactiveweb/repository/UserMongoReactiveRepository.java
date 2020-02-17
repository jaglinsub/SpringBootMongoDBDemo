package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserMongoReactiveRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByEmail(String email);
    Mono<User> findUserById(String id);

    Flux<User> findAll(Example<User> of);

    @Query(value="{ 'parentUser.$id' : ?0 }")
//    @Query(value="{ 'parentUser.$id' : ObjectId(?0)}")
    Flux<User> findByParentUserId(ObjectId id);
}
