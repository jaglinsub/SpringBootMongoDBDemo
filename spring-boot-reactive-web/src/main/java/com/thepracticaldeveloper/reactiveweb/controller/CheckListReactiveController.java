package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.CareerCheckListItems;
import com.thepracticaldeveloper.reactiveweb.domain.CareerGoals;
import com.thepracticaldeveloper.reactiveweb.domain.DefaultCareerCheckListItems;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
public class CheckListReactiveController {

    private static final Logger log = LoggerFactory.getLogger(CheckListReactiveController.class);
    private DefaultCareerCheckListItemsMongoReactiveRepository defaultCareerCheckListItemsMongoReactiveRepository;
    private CareerCheckListItemsMongoReactiveRepository careerCheckListItemsMongoReactiveRepository;
    private CareerCheckListMongoReactiveRepository careerCheckListMongoReactiveRepository;
    private CareerGoalsMongoReactiveRepository careerGoalsMongoReactiveRepository;

    public CheckListReactiveController(final DefaultCareerCheckListItemsMongoReactiveRepository defaultCareerCheckListItemsMongoReactiveRepository,
                                       final CareerCheckListItemsMongoReactiveRepository careerCheckListItemsMongoReactiveRepository,
                                       final CareerCheckListMongoReactiveRepository careerCheckListMongoReactiveRepository,
                                       final CareerGoalsMongoReactiveRepository careerGoalsMongoReactiveRepository
                                        ) {
        this.defaultCareerCheckListItemsMongoReactiveRepository = defaultCareerCheckListItemsMongoReactiveRepository;
        this.careerCheckListItemsMongoReactiveRepository = careerCheckListItemsMongoReactiveRepository;
        this.careerCheckListMongoReactiveRepository = careerCheckListMongoReactiveRepository;
        this.careerGoalsMongoReactiveRepository = careerGoalsMongoReactiveRepository;
    }
    @GetMapping()
    public CareerGoals getCareerGoalsByUserId(final @RequestParam(name = "userid") String id) {
        CareerGoals careerGoals = careerGoalsMongoReactiveRepository.findCareerGoalsByUserId(id).block();
        if(careerGoals == null) {
            careerGoals = new CareerGoals();
            careerGoals.setUserId(id);

            Flux<DefaultCareerCheckListItems> defaultCareerCheckListItemsFlux = this.defaultCareerCheckListItemsMongoReactiveRepository.findAll();
            List<DefaultCareerCheckListItems> defaultCareerCheckListItemsList =  defaultCareerCheckListItemsFlux.collectList().block();
//          // CareerCheckListItems[] careerCheckListItemsArr = new CareerCheckListItems[careerCheckListItemsList.size()];
//           // careerCheckListItemsArr = careerCheckListItemsList.toArray(careerCheckListItemsArr);

            for (DefaultCareerCheckListItems careerCheckListItems: defaultCareerCheckListItemsList) {
                System.out.println("defaultCareerCheckListItems= " + careerCheckListItems.toString());

            }
            
        }
        System.out.println("careerGoals = " + careerGoals.toString());
        return careerGoals;
    }

    @PostMapping("/savecareergoals")
    @ResponseStatus(HttpStatus.CREATED)
    CareerGoals create(@RequestBody CareerGoals userEntry) {
        if(userEntry != null) {
            log.info("Entire Profile Object=" + userEntry.toString());
        }
        return this.careerGoalsMongoReactiveRepository.save(userEntry).block();
    }
}
