package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.CareerCheckListItems;
import com.thepracticaldeveloper.reactiveweb.domain.DefaultCareerCheckListItems;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CareerCheckListItemsMongoReactiveRepository extends ReactiveCrudRepository<CareerCheckListItems, String> {
    //Mono<Interests> findInterestsByUser(String id);
}
