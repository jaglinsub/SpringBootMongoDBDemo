package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.AnswerOptions;
import com.thepracticaldeveloper.reactiveweb.domain.InterestOptions;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnswerOptionsMongoReactiveRepository extends ReactiveCrudRepository<AnswerOptions, String> {

}
