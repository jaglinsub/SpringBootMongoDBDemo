package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.OpportunityDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OpportunityDetailsMongoReactiveRepository extends ReactiveCrudRepository<OpportunityDetails, String> {

}
