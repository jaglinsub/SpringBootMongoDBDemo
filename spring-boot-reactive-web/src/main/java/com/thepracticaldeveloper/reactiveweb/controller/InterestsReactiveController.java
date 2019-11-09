package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import com.thepracticaldeveloper.reactiveweb.repository.AnswerOptionsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.InterestOptionsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.InterestsMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
public class InterestsReactiveController {

    private static final Logger log = LoggerFactory.getLogger(InterestsReactiveController.class);
    private InterestsMongoReactiveRepository interestsMongoReactiveRepository;
    private InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository;
    private AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository;

    public InterestsReactiveController(final InterestsMongoReactiveRepository interestsMongoReactiveRepository, final InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository, final AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository) {
        this.interestsMongoReactiveRepository = interestsMongoReactiveRepository;
        this.interestOptionsMongoReactiveRepository = interestOptionsMongoReactiveRepository;
        this.answerOptionsMongoReactiveRepository = answerOptionsMongoReactiveRepository;
    }
    @GetMapping("/")
    public Flux<InterestOptions> getInterestOptions() throws Exception {
        return interestOptionsMongoReactiveRepository.findAll();
    }
    @GetMapping()
    public Interests getInterestsByUserId(final @RequestParam(name = "userid") String id) {
        Interests interests = interestsMongoReactiveRepository.findInterestsByUserId(id).block();
        if(interests != null) {
            return interests;
        }
        else {
            interests = new Interests();
//            interests.setUserId(id);
            try {

                Flux<String> flux = Flux.just(
                        "Site_0:grokonez.com",
                        "Description_0:Java Technology",
                        "Description_1:Spring Framework");

                System.out.println("=== flux.collectList() ===");
                List<String> list1 = flux.collectList().block();
                list1.forEach(System.out::println);

                Flux<InterestOptions> interestOptionsFlux = getInterestOptions();
                List<InterestOptions> interestOptionsList =  interestOptionsFlux.collectList().block();
                int qCount = 0;
                InterestOptions[] interestOptionsArr = new InterestOptions[interestOptionsList.size()];
                interestOptionsArr = interestOptionsList.toArray(interestOptionsArr);

                for (InterestOptions into: interestOptionsList) {
                    System.out.println("Qs=" + into.getInterestQuestion());

                }

                interests.setInterestOptions(interestOptionsArr);
                //interestOptionsFlux.collectList().flatMap(interestOptions -> {
                    //interestOptionsArr.add(interestOptions);
                    //System.out.println("interestOptions.getInterestQuestion()=" + interestOptions..getInterestQuestion());
                    //finalInterests.setInterestOptions((InterestOptions[]) interestOptions.toArray());
                //}).subscribe();
                //interests.setInterestOptions((InterestOptions[]) interestOptionsArr.toArray());
                //interests.setInterestOptions(i);
            }
            catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return interests;
    }

    @PostMapping("/saveinterests")
    @ResponseStatus(HttpStatus.CREATED)
    Interests create(@RequestBody Interests userEntry) {
        if(userEntry != null) {
            log.info("Entire Interest Object=" + userEntry.toString());
        }
        return this.interestsMongoReactiveRepository.save(userEntry).block();
    }
}
