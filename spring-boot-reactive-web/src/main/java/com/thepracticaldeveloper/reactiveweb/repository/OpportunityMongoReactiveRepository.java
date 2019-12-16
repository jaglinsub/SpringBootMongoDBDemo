package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Opportunity;
import com.thepracticaldeveloper.reactiveweb.domain.OpportunityDetails;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OpportunityMongoReactiveRepository extends ReactiveCrudRepository<Opportunity, String> {
    @Query(value = "{}", fields="{ 'opportunityName' : 1, 'opportunityShortDesc' : 1, 'organizationName' : 1, 'organizationAddress' : 1, 'whenPosted' : 1, 'typeofProfOppurtunity' : 1}")
    //@Query(value = "{}", fields="{ 'opportunityName' : 1, 'opportunityShortDesc' : 1, 'opportunityDetails' : {'$ref' : 'opportunityDetails', '$id' : 1 }}")
    Flux<Opportunity> findAllOpportunity();

    Mono<Opportunity> findOpportunityById(String id);
}
