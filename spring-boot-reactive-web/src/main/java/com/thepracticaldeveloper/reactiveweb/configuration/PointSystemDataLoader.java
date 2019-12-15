package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.PointSystem;
import com.thepracticaldeveloper.reactiveweb.repository.PointSystemMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

@Component
public class PointSystemDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PointSystemDataLoader.class);
    private PointSystemMongoReactiveRepository pointSystemMongoReactiveRepository;

    PointSystemDataLoader(final PointSystemMongoReactiveRepository pointSystemMongoReactiveRepository) {
        this.pointSystemMongoReactiveRepository = pointSystemMongoReactiveRepository;
    }

@Override
    public void run(final String... args) throws Exception {
        try {
            if (pointSystemMongoReactiveRepository.count().block() > 0L) {
                return;
            }
            Hashtable<String, String[]> intTable = new Hashtable<String, String[]>();
            intTable.put("Intership Exp", new String[]{"10", "1"});
            intTable.put("Volunteering Exp", new String[]{"7", "2"});
            intTable.put("Student Org. Involvement", new String[]{"4", "3"});
            intTable.put("Calls with Professionals", new String[]{"4", "4"});
            intTable.put("Career Research", new String[]{"3", "5"});

            intTable.forEach( (k, v) -> {
                PointSystem pointSystem = new PointSystem();
                pointSystem.setActivityName(k);
                for (int i = 0; i < v.length; i++) {
                    pointSystem.setActivityPoint(v[0]);
                    pointSystem.setDisplayOrder(Integer.parseInt(v[1]));
                }
                pointSystemMongoReactiveRepository.save(pointSystem).block();
            });
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
