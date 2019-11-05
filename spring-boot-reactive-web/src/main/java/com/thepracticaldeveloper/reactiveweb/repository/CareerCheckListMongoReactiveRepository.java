package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.CareerCheckList;
import com.thepracticaldeveloper.reactiveweb.domain.CareerCheckListItems;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CareerCheckListMongoReactiveRepository extends ReactiveCrudRepository<CareerCheckList, String> {
    //Mono<Interests> findInterestsByUser(String id);
}
