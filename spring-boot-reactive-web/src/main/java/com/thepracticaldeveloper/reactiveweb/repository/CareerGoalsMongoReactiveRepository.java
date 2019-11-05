package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.CareerCheckList;
import com.thepracticaldeveloper.reactiveweb.domain.CareerGoals;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CareerGoalsMongoReactiveRepository extends ReactiveCrudRepository<CareerGoals, String> {
    Mono<CareerGoals> findCareerGoalsByUserId(String id);
}
