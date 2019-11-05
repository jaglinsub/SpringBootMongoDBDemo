package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.AnswerOptions;
import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import com.thepracticaldeveloper.reactiveweb.repository.AnswerOptionsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.InterestOptionsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserTypeMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InterestOptionsDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(InterestOptionsDataLoader.class);

    private InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository;
    private AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository;

    InterestOptionsDataLoader(final InterestOptionsMongoReactiveRepository interestOptionsMongoReactiveRepository, final AnswerOptionsMongoReactiveRepository answerOptionsMongoReactiveRepository) {
        this.interestOptionsMongoReactiveRepository = interestOptionsMongoReactiveRepository;
        this.answerOptionsMongoReactiveRepository = answerOptionsMongoReactiveRepository;
    }


    public void run2(final String... args) throws Exception {
        try {
            AnswerOptions answerOptions = new AnswerOptions();
            answerOptions.setAnswerName("With A Team");
            answerOptionsMongoReactiveRepository.save(answerOptions).block();

            AnswerOptions answerOptions1 = new AnswerOptions();
            answerOptions1.setAnswerName("Independently");
            answerOptionsMongoReactiveRepository.save(answerOptions1).block();

            AnswerOptions answerOptionsArr[] = {answerOptions, answerOptions1};

            InterestOptions interestOptions = new InterestOptions();
            interestOptions.setInterestQuestionName("Test");
            interestOptions.setInterestQuestion("What is test");
            interestOptions.setDisplayOrder(1);
            interestOptions.setAnswerOptions(answerOptionsArr);
            interestOptionsMongoReactiveRepository.save(interestOptions).block();

        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void run(final String... args) throws Exception {

    }

}
