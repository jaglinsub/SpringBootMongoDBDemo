package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.AnswerOptions;
import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.repository.AnswerOptionsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.InterestOptionsMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InterestOptionsDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(InterestOptionsDataLoader.class);

    private InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository;
    private AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository;

    InterestOptionsDataLoader(final InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository, final AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository) {
        this.interestOptionsMongoReactiveRepository = interestOptionsMongoReactiveRepository;
        this.answerOptionsMongoReactiveRepository = answerOptionsMongoReactiveRepository;
    }

@Override
    public void run(final String... args) throws Exception {
        int iCnt = 10;
        if (iCnt < 100) {
            return;
        }
        try {
            if (interestOptionsMongoReactiveRepository.count().block() > 0L) {
                return;
            }
            Hashtable<String, String[]> intTable = new Hashtable<String, String[]>();
            intTable.put("I’m looking for more opportunities to", new String[]{"talk to mentors", "explore different types of jobs", "get more hands on experience", "access internships", "build a resume"});
            intTable.put("At school, when I’m given a project, I’d rather work", new String[]{"on my own", "with a team", "on my own and with a team"});
            intTable.put("My favorite activity to do outside of school is", new String[]{"play sports", "spend time with friends", "work on side projects", "play videogames", "read", "articipate in after school clubs/activities"});
            intTable.put("Ideally, before I graduate I want to know how to", new String[]{"improve writing skills", "give a good presentation", "lead a team", "think critically", "be a strong teammate"});
            intTable.put("After I graduate high school, I think I want to be a", new String[]{"Medical Doctor", "EMT/Paramedic", "Nurse", "Therapist", "Pharmacist", "Dentist", "Athletic Trainer", "Nutritionist", "Physician", "Surgeon", "Veterinarian", "Bio-tech Coordinator", "Forensics Pathologist", "Healthcare Administrator", "Hospital Operational Engineer", "Psychologist", "Something in the medical field", "Other - not included in this list"});

            AtomicInteger displayOrder = new AtomicInteger(1);
            intTable.forEach( (k, v) -> {

                AnswerOptions answerOptionsArr[] = new AnswerOptions[v.length];
                for (int i = 0; i < v.length; i++) {
                    AnswerOptions answerOptions = new AnswerOptions();
                    answerOptions.setAnswerName(v[i]);
                    answerOptions = answerOptionsMongoReactiveRepository.save(answerOptions).block();
                    answerOptionsArr[i] = answerOptions;
                }
                InterestOptions interestOptions = new InterestOptions();
                interestOptions.setInterestQuestionName(k);
                interestOptions.setInterestQuestion(k);
                interestOptions.setDisplayOrder(displayOrder.getAndIncrement());
                interestOptions.setAnswerOptions(answerOptionsArr);
                interestOptionsMongoReactiveRepository.save(interestOptions).block();
            });

            /*AnswerOptions answerOptions = new AnswerOptions();
            answerOptions.setAnswerName("Learn Real-World Skills");
            answerOptionsMongoReactiveRepository.save(answerOptions).block();

            AnswerOptions answerOptions1 = new AnswerOptions();
            answerOptions1.setAnswerName("Build A Career");
            answerOptionsMongoReactiveRepository.save(answerOptions1).block();

            AnswerOptions answerOptionsArr[] = {answerOptions, answerOptions1};

            InterestOptions interestOptions = new InterestOptions();
            interestOptions.setInterestQuestionName("Looking for");
            interestOptions.setInterestQuestion("I’m looking for more opportunities to");
            interestOptions.setDisplayOrder(1);
            interestOptions.setAnswerOptions(answerOptionsArr);
            interestOptionsMongoReactiveRepository.save(interestOptions).block();*/

        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    //@Override
    public void run2(final String... args) throws Exception {

    }

}
