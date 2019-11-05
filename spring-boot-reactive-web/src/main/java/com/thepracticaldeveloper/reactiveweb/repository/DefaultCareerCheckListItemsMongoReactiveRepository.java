package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.DefaultCareerCheckListItems;
import com.thepracticaldeveloper.reactiveweb.domain.Experience;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DefaultCareerCheckListItemsMongoReactiveRepository extends ReactiveCrudRepository<DefaultCareerCheckListItems, String> {
    //Mono<Interests> findInterestsByUser(String id);
}
