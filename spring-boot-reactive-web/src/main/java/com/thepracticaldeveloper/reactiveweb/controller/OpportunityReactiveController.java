package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.*;
import com.thepracticaldeveloper.reactiveweb.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@RequestMapping("/api/opportunity")
public class OpportunityReactiveController {

    private static final Logger log = LoggerFactory.getLogger(OpportunityReactiveController.class);
    private OpportunityMongoReactiveRepository opportunityMongoReactiveRepository;
    private OpportunityDetailsMongoReactiveRepository opportunityDetailsMongoReactiveRepository;
    private SavedOpputtunitiesMongoReactiveRepository savedOpputtunitiesMongoReactiveRepository;

    public OpportunityReactiveController(final OpportunityMongoReactiveRepository opportunityMongoReactiveRepository, final OpportunityDetailsMongoReactiveRepository opportunityDetailsMongoReactiveRepository, final SavedOpputtunitiesMongoReactiveRepository savedOpputtunitiesMongoReactiveRepository) {
        this.opportunityMongoReactiveRepository = opportunityMongoReactiveRepository;
        this.opportunityDetailsMongoReactiveRepository = opportunityDetailsMongoReactiveRepository;
        this.savedOpputtunitiesMongoReactiveRepository = savedOpputtunitiesMongoReactiveRepository;
    }
    @GetMapping("/")
    public Flux<Opportunity> getOpportunities() throws Exception {
//        return opportunityMongoReactiveRepository.findAll();

        Query query = new Query();
        query.fields().exclude("opportunityDetails.organizationImage");
//        opportunityMongoReactiveRepository.findAll(query, );
        return opportunityMongoReactiveRepository.findAllOpportunity();
    }
    @GetMapping("/id")
    public Opportunity getOpportunityById(final @RequestParam(name = "id") String id) {
        return opportunityMongoReactiveRepository.findOpportunityById(id).block();
    }

    @PostMapping("/saveopportunity")
    @ResponseStatus(HttpStatus.CREATED)
    Opportunity create(@RequestBody Opportunity userEntry) {
        if(userEntry != null) {
            log.info("Entire Opportunity Object=" + userEntry.toString());
        }
        return this.opportunityMongoReactiveRepository.save(userEntry).block();
    }

    @PostMapping("/saveuseropportunities")
    @ResponseStatus(HttpStatus.CREATED)
    SavedOpportunities saveOppurtunitiesforUser(@RequestBody SavedOpportunities userEntry) {
        if(userEntry != null) {
            log.info("Entire Saved Opportunity Object=" + userEntry.toString());
        }
        return this.savedOpputtunitiesMongoReactiveRepository.save(userEntry).block();
    }

    @GetMapping("/savedopportunities")
    public Opportunity[] getSavedOppurtunitiesByUserId(final @RequestParam(name = "userid") String id) {

        List<SavedOpportunities> listSavedOpportunities = this.savedOpputtunitiesMongoReactiveRepository.findProfileByUserId(id).collectList().block();
        Opportunity[] opportunities = new Opportunity[0];
        if(listSavedOpportunities != null) {
            opportunities = new Opportunity[listSavedOpportunities.size()];
            AtomicInteger opportunitiesIncr = new AtomicInteger(0);
            Opportunity[] finalOpportunities = opportunities;
            listSavedOpportunities.forEach(oppur -> {
                System.out.println("Saved Oppurtunity= " + oppur.getOppurtunityId() + " : " + oppur.getUserId());
                Opportunity opportunity = this.opportunityMongoReactiveRepository.findOpportunityById(oppur.getOppurtunityId()).block();
                finalOpportunities[opportunitiesIncr.getAndIncrement()] = opportunity;
                System.out.println("Saved Oppurtunity=opportunity= " + opportunity.getId() + " : " + opportunity.getOpportunityName());

            });
            opportunities = finalOpportunities;
        }
        return  opportunities;
    }
}
