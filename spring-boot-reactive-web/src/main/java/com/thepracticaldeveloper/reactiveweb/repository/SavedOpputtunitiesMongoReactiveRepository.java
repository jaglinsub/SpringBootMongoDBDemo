package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.domain.SavedOpportunities;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavedOpputtunitiesMongoReactiveRepository extends ReactiveCrudRepository<SavedOpportunities, String> {
    Flux<SavedOpportunities> findProfileByUserId(String id);
}
