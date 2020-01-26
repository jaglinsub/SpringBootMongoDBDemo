package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.*;
import com.thepracticaldeveloper.reactiveweb.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CareerCheckListDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CareerCheckListDataLoader.class);

    private DefaultCareerCheckListItemsMongoReactiveRepository defaultCareerCheckListItemsMongoReactiveRepository;
    private CareerCheckListItemsMongoReactiveRepository careerCheckListItemsMongoReactiveRepository;
    private CareerCheckListMongoReactiveRepository careerCheckListMongoReactiveRepository;
    private CareerGoalsMongoReactiveRepository careerGoalsMongoReactiveRepository;

    CareerCheckListDataLoader(final DefaultCareerCheckListItemsMongoReactiveRepository defaultCareerCheckListItemsMongoReactiveRepository,
                              final CareerCheckListItemsMongoReactiveRepository careerCheckListItemsMongoReactiveRepository,
                              final CareerCheckListMongoReactiveRepository careerCheckListMongoReactiveRepository,
                              final CareerGoalsMongoReactiveRepository careerGoalsMongoReactiveRepository
    ) {
        this.defaultCareerCheckListItemsMongoReactiveRepository = defaultCareerCheckListItemsMongoReactiveRepository;
        this.careerCheckListItemsMongoReactiveRepository = careerCheckListItemsMongoReactiveRepository;
        this.careerCheckListMongoReactiveRepository = careerCheckListMongoReactiveRepository;
        this.careerGoalsMongoReactiveRepository = careerGoalsMongoReactiveRepository;
    }


    public void run2(final String... args) throws Exception {

    }

@Override
    public void run(final String... args) throws Exception {
        int iCnt = 10;
        if (iCnt < 100) {
            return;
        }
        try {
            DefaultCareerCheckListItems defaultCareerCheckListItems = new DefaultCareerCheckListItems();
            String arr[] = {"GS Checklist item", "GI Checklist item", "GE Checklist item"};
            DefaultCareerCheckListItems defaultCareerCheckListItemsArr[] = new DefaultCareerCheckListItems[6];
            for(int i = 0; i< arr.length; i++){
                for(int j = 0; j< 6; j++) {
                    defaultCareerCheckListItems = new DefaultCareerCheckListItems();
                    defaultCareerCheckListItems.setCheckListItemTitle(arr[i] + " " + (j + 1));
                    defaultCareerCheckListItems.setPoints((j + 1));
                    defaultCareerCheckListItems.setDisplayOrder((j + 1));
//                    defaultCareerCheckListItems.setSelected((j % 2 == 0));
                    defaultCareerCheckListItems = this.defaultCareerCheckListItemsMongoReactiveRepository.save(defaultCareerCheckListItems).block();
                    defaultCareerCheckListItemsArr[j] = defaultCareerCheckListItems;
                }
            }
            CareerCheckListItems careerCheckListItems = new CareerCheckListItems();
            careerCheckListItems.setUserId("jagan");
            //careerCheckListItems.setId(defaultCareerCheckListItemsArr[2].getId());
            careerCheckListItems.setSelected(true);
            careerCheckListItemsMongoReactiveRepository.save(careerCheckListItems).block();

            careerCheckListItems = new CareerCheckListItems();
            careerCheckListItems.setUserId("jagan");
            //careerCheckListItems.setId(defaultCareerCheckListItemsArr[1].getId());
            careerCheckListItems.setSelected(false);

            careerCheckListItemsMongoReactiveRepository.save(careerCheckListItems).block();

        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

}
